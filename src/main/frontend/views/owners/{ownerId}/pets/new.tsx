import {
    DatePicker,
    FormItem,
    FormLayout,
    TextField,
    VerticalLayout
} from "@vaadin/react-components";
import {translate} from "@vaadin/hilla-react-i18n";
import {useForm} from "@vaadin/hilla-react-form"
import {Button} from "@vaadin/react-components/Button.js";
import {useNavigate} from "react-router-dom";
import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import PetModel
    from "../../../../generated/org/springframework/samples/petclinic/backend/owner/PetModel";
import {PetService} from "../../../../generated/endpoints";


export const config: ViewConfig = {
    menu: { exclude: true}
};

export default function NewPetView() {
    const navigate = useNavigate();
  const { model, submit,  field } = useForm(PetModel, {
      onSubmit: async (pet) => {
          const savedPet = await PetService.save(pet);
          if (savedPet) {
              navigate('/owners/' + savedPet.id);
          }
      }
  });

  return (
      <>
          <VerticalLayout theme="padding spacing"
                      className="w-full justify-center">
          <FormLayout
              responsiveSteps={[{ minWidth: '0', columns: 1 },
                  { minWidth: '600px', columns: 1 }]

              }>
              <h2>{translate('newPet')}</h2>
              <FormItem>
                  <label slot="label">{translate('firstName')}</label>
                  <TextField {...field(model.name)}></TextField>
              </FormItem>
              <FormItem>
                  <label slot="label">{translate('lastName')}</label>
                  <DatePicker {...field(model.birthDate)}></DatePicker>
              </FormItem>
              <FormItem>
                  <label slot="label">{translate('address')}</label>
                  <TextField {...field(model.type)}></TextField>
              </FormItem>
              <FormItem>
                  <Button onClick={submit}>{translate('addPet')}</Button>
              </FormItem>
          </FormLayout>
          </VerticalLayout>
      </>
  );
}
