import {
    ComboBox,
    DatePicker,
    FormItem,
    FormLayout, Grid, GridColumn,
    TextField,
    VerticalLayout
} from "@vaadin/react-components";
import {translate} from "@vaadin/hilla-react-i18n";
import {useForm} from "@vaadin/hilla-react-form"
import {Button} from "@vaadin/react-components/Button.js";
import {useNavigate, useParams} from "react-router-dom";
import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import PetModel
    from "../../../../../../generated/org/springframework/samples/petclinic/backend/owner/PetModel";
import {
    OwnerService,
    PetService,
    VisitService
} from "../../../../../../generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import PetType
    from "../../../../../../generated/org/springframework/samples/petclinic/backend/owner/PetType";
import {useEffect} from "react";
import VisitCreateRecordModel
    from "../../../../../../generated/org/springframework/samples/petclinic/endpoint/VisitCreateRecordModel";


export const config: ViewConfig = {
    menu: { exclude: true}
};

export default function NewVisitView() {
    const { ownerId, petId } = useParams();
    const items = useSignal<PetType[]>([]);
    useEffect(() => {
        PetService.findPetTypes().then((data) => {
            items.value = data;
        });
        VisitService.get(Number(ownerId), Number(petId)).then(read);
    }, []);
    const navigate = useNavigate();
  const { read, model, submit,  field } = useForm(VisitCreateRecordModel, {
      onSubmit: async (visitCreateDto) => {
          const savedVisit = await VisitService.saveVisit(visitCreateDto);
          if (savedVisit) {
              navigate('/owners/' + ownerId);
          }
      }
  });

  return (
      <>
          <VerticalLayout theme="padding spacing"
                          className="w-full justify-center">
              <h2>{translate('newVisit')}</h2>

              <h2>{translate('pet')}</h2>
              <FormLayout
                  responsiveSteps={[{minWidth: '0', columns: 1},
                      {minWidth: '600px', columns: 4}]
                  }>
                  <TextField label={translate('name')}
                              readonly {...field(model.petName)}></TextField>
                  <DatePicker label={translate('birthDate')}
                              readonly {...field(model.petBirthDate)}></DatePicker>

                  <TextField label={translate('type')}
                             readonly {...field(model.petType)}></TextField>
                  <TextField label={translate('owner')}
                             readonly {...field(model.petOwner)}></TextField>
              </FormLayout>
              <FormLayout
                  responsiveSteps={[{minWidth: '0', columns: 1},
                      {minWidth: '600px', columns: 1}]
                  }>
                  <FormItem>
                      <label slot="label">{translate('visitDate')}</label>
                      <DatePicker {...field(model.visitDate)}></DatePicker>
                  </FormItem>
                  <FormItem>
                      <label slot="label">{translate('description')}</label>
                      <TextField {...field(model.description)} className="w-full"></TextField>
                  </FormItem>
                  <FormItem>
                      <Button onClick={submit}>{translate('addVisit')}</Button>
                  </FormItem>
              </FormLayout>
              {(model.previousVisits) ?
                  <Grid items={Array.from(model.previousVisits)} allRowsVisible>
                      <GridColumn path="value.date" header={translate('visitDate')} />
                      <GridColumn path="value.description" header={translate('description')} />
                  </Grid>
                  : <VerticalLayout></VerticalLayout>
              }

          </VerticalLayout>
      </>
  );
}
