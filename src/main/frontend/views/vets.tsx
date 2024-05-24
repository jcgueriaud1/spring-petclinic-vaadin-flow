import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import {Grid, GridColumn, VerticalLayout} from "@vaadin/react-components";
import Vet
    from "../generated/org/springframework/samples/petclinic/backend/vet/Vet";
import {useEffect} from "react";
import { findAllVets } from '../generated/VetService';
import {translate} from "@vaadin/hilla-react-i18n";

export const config: ViewConfig = {
  menu: { order: 0, icon: 'vaadin:list' },
  title: 'veterinarians',
};

const statusRenderer = (person: Vet) => (
    <span>
    {person.specialties.map(sp => sp.name).join(", ")}
  </span>
);

export default function VetsView() {
  const name = useSignal('');
    const items = useSignal<Vet[]>([]);
    useEffect(() => {
        findAllVets().then((value) => {
            items.value =  value.map((person) => ({
                ...person,
                fullName: `${person.firstName} ${person.lastName}`,
            }));
        })
    }, []);
  return (
      <>
          <VerticalLayout theme="padding spacing"
                          className="w-full justify-center">
              <h2>{translate('veterinarians')}</h2>
              <Grid items={items.value}>
                  <GridColumn path="fullName" header={translate('name')} />
                  <GridColumn header={translate('specialties')} >
                      {({ item }) => statusRenderer(item)}
                  </GridColumn>
              </Grid>
          </VerticalLayout>
      </>
  );
}
