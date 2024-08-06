import {Icon} from "@vaadin/react-components";
import {Button} from "@vaadin/react-components/Button";
import {Signal, signal} from "@vaadin/hilla-react-signals";
import {ValidationError} from "@vaadin/hilla-lit-form";
import {EndpointError} from "@vaadin/hilla-frontend";
import {KeyboardEvent} from "react";

interface ValidationErrorInterface {
    focus: boolean;
    errors: unknown;
}
export const validationErrorSignal: Signal<ValidationErrorInterface> = signal({errors: null, focus:false});

export function handleKeyDown(event: KeyboardEvent, submit: any) {
    if (event.key === 'Enter') {
        void handleSubmit(submit, true);
    }
}

export async function handleSubmit(submit: any, focus: boolean = false): Promise<void> {
    try {
        validationErrorSignal.value.errors = null;
        validationErrorSignal.value.focus = false;
        await submit();
    } catch (error) {
        validationErrorSignal.value.errors = error;
        validationErrorSignal.value.focus = focus;
    }
}
export default function ValidationErrors() {
    const error = validationErrorSignal.value.errors;
    const focus = validationErrorSignal.value.focus;
    if (error != null) {
        if (error instanceof ValidationError) {
            const nonPropertyErrorMessages = error.errors
                .filter((validationError) => validationError.message);
            if (nonPropertyErrorMessages.length > 0) {
                return <div
                    className="flex bg-error-10 rounded-m p-s gap-x-s gap-y-s"
                    role="group" aria-labelledby="validation-errors-container"
                    tabIndex={-1}>
                    <div
                        className="flex flex-shrink-0 h-xs mt-xs ms-xs text-error w-xs items-center justify-center">
                        <Icon className="icon-s" icon="vaadin:warning"></Icon>
                    </div>
                    <div className="flex my-xs flex-col"><h3
                        className="text-m leading-m"
                        id="validation-errors-container">There are {nonPropertyErrorMessages.length} errors:</h3>
                        <ol className="text-s my-0 ps-m">
                            {nonPropertyErrorMessages.map((valueError, index) => (
                                <li key={index}>
                                    <Button className="text-secondary"
                                            theme="tertiary-inline"
                                            tabindex={0} role="button" onClick={() => {
                                                const inputElement = document.querySelector(`[name="${valueError.property}"]`);
                                                if (inputElement && (inputElement instanceof HTMLElement)) {
                                                    inputElement.focus();
                                                }
                                    }}>{valueError.validatorMessage?? valueError.message}
                                    </Button>
                                </li>
                            ))}
                        </ol>
                    </div>
                </div>;
            }
        } else if (error instanceof EndpointError) {
            return <>test</>
        } else {
            throw error;
        }
    }

    return <></>
}


function handleSubmitError() {
    const error = validationErrorSignal.value;
    if (error instanceof ValidationError) {
        const nonPropertyErrorMessages = error.errors
            .filter((validationError) => !validationError.property)
            .map((validationError) => validationError.validatorMessage ?? validationError.message);
        if (nonPropertyErrorMessages.length > 0) {
            return <>
                Validation errors:
                <ul>
                    {nonPropertyErrorMessages.map((message, index) => (
                        <li key={index}>{message}</li>
                    ))}
                </ul>
            </>;
        } else {
            return <></>
        }
    } else if (error instanceof EndpointError) {
        return <>test</>
    } else {
        throw error;
    }
}
