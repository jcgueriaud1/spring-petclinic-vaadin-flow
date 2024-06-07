import {
    DatePicker,
    FormItem,
    FormLayout,
    TextField,
    VerticalLayout
} from "@vaadin/react-components";
import {translate} from "@vaadin/hilla-react-i18n";
import {useForm} from "@vaadin/hilla-react-form";
import OwnerModel
    from "../../../generated/org/springframework/samples/petclinic/backend/owner/OwnerModel";
import {Button} from "@vaadin/react-components/Button.js";
import {OwnerService} from "../../../generated/endpoints";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {HorizontalLayout} from "@vaadin/react-components/HorizontalLayout.js";

export const config: ViewConfig = {
    menu: { exclude: true}
};

export default function ViewOwnerView() {
    const { ownerId } = useParams();
    const navigate = useNavigate();
    const { read, model, submit,  field } = useForm(OwnerModel, {
        onSubmit: async (owner) => {
            const savedOwner = await OwnerService.save(owner);
            if (savedOwner) {
                navigate('/owners/' + savedOwner.id);
            }
        }
    });

    useEffect(() => {
        OwnerService.get(Number(ownerId)).then(read);
    }, [ownerId])
    return (
        <>
            <VerticalLayout theme="padding spacing"
                            className="w-full justify-center">
                <FormLayout
                    responsiveSteps={[{minWidth: '0', columns: 1},
                        {minWidth: '600px', columns: 1}]

                    }>
                    <h2>{translate('ownerInformation')}</h2>
                    <FormItem>
                        <label slot="label">{translate('firstName')}</label>
                        <TextField
                            readonly {...field(model.firstName)}></TextField>
                    </FormItem>
                    <FormItem>
                        <label slot="label">{translate('lastName')}</label>
                        <TextField
                            readonly {...field(model.lastName)}></TextField>
                    </FormItem>
                    <FormItem>
                        <label slot="label">{translate('address')}</label>
                        <TextField
                            readonly {...field(model.address)}></TextField>
                    </FormItem>
                    <FormItem>
                        <label slot="label">{translate('city')}</label>
                        <TextField readonly {...field(model.city)}></TextField>
                    </FormItem>
                    <FormItem>
                        <label slot="label">{translate('telephone')}</label>
                        <TextField
                            readonly {...field(model.telephone)}></TextField>
                    </FormItem>
                    <FormItem>
                        <HorizontalLayout theme="spacing">
                            <Button onClick={(e) => {
                                navigate('/owners/' + ownerId + '/edit')
                            }}>{translate('editOwner')}</Button>
                            <Button onClick={(e) => {
                                navigate('/owners/' + ownerId + '/pets/new')
                            }}>{translate('addNewPet')}</Button>
                        </HorizontalLayout>
                    </FormItem>
                    <VerticalLayout theme="spacing" className="pet-container">
                        <h2>{translate('petsAndVisits')}</h2>

                        {
                            Array.from(model.pets, (pet) => (
                            <FormLayout key={`${pet.value!.id}`}
                                responsiveSteps={[{minWidth: '0', columns: 1},
                                    {minWidth: '600px', columns: 1}]

                                }>
                                <hr/>
                                <FormItem>
                                    <label slot="label">{translate('name')}</label>
                                    <TextField
                                        readonly {...field(pet.model.name)}></TextField>
                                </FormItem>
                                <FormItem>
                                    <label slot="label">{translate('birthDate')}</label>
                                    <DatePicker
                                        readonly {...field(pet.model.birthDate)}></DatePicker>
                                </FormItem>
                                <FormItem>
                                    <label slot="label">{translate('type')}</label>
                                    <TextField
                                        readonly {...field(pet.model.type)}></TextField>
                                </FormItem>
                            </FormLayout>
                            ))
                        }
                    </VerticalLayout>
                </FormLayout>
            </VerticalLayout>
        </>
    );
}
