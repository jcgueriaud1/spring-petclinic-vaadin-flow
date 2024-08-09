import {Icon} from "@vaadin/react-components";
import {Button} from "@vaadin/react-components/Button";
import {Signal, signal} from "@vaadin/hilla-react-signals";
import {ValidationError} from "@vaadin/hilla-lit-form";
import {EndpointError} from "@vaadin/hilla-frontend";
import {KeyboardEvent, useEffect, useRef} from "react";

export const validationErrorSignal: Signal<unknown> = signal(null);

export function handleKeyDown(event: KeyboardEvent, submit: any) {
    if (event.key === 'Enter') {
        void handleSubmit(submit);
    }
}

export async function handleSubmit(submit: any): Promise<void> {
    try {
        validationErrorSignal.value = null;
        await submit();
    } catch (error) {
        validationErrorSignal.value = error;
    }
}

export default function ValidationErrors() {
    const error = validationErrorSignal.value;
    const containerRef = useRef(null);
    useEffect(() => {
        if (containerRef && (containerRef.current)) {
            (containerRef.current as any).focus();
        }
    }, [error]);
    if (error != null) {
        if (error instanceof ValidationError) {
            const nonPropertyErrorMessages = error.errors
                .filter((validationError) => validationError.message);
            if (nonPropertyErrorMessages.length > 0) {
                return <div
                    className="flex bg-error-10 rounded-m p-s gap-x-s gap-y-s"
                    role="group" aria-labelledby="validation-errors-title"
                    id="validation-errors-container" ref={containerRef}
                    tabIndex={-1}>
                    <div
                        className="flex flex-shrink-0 h-xs mt-xs ms-xs text-error w-xs items-center justify-center">
                        <Icon className="icon-s" icon="vaadin:warning"></Icon>
                    </div>
                    <div className="flex my-xs flex-col"><h3
                        className="text-m leading-m"
                        id="validation-errors-title">There
                        are {nonPropertyErrorMessages.length} errors:</h3>
                        <ol className="text-s my-0 ps-m">
                            {nonPropertyErrorMessages.map((valueError, index) => (
                                <li key={index}>
                                    <Button className="text-secondary"
                                            theme="tertiary-inline"
                                            tabindex={0} role="button"
                                            onClick={() => {
                                                const inputElement = document.querySelector(`[name="${valueError.property}"]`);
                                                if (inputElement && (inputElement instanceof HTMLElement)) {
                                                    inputElement.focus();
                                                }
                                            }}>{valueError.validatorMessage ?? valueError.message}
                                    </Button>
                                </li>
                            ))}
                        </ol>
                    </div>
                </div>;
            }
        } else if (error instanceof EndpointError) {
            return <></>
        } else {
            throw error;
        }
    }

    return <></>
}
