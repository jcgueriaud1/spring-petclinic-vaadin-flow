import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import {Grid, GridColumn, VerticalLayout} from "@vaadin/react-components";
import Vet
    from "../generated/org/springframework/samples/petclinic/backend/vet/Vet";
import {useEffect} from "react";
import { findAllVets } from '../generated/VetService';

export const config: ViewConfig = {
  menu: { order: 0, icon: 'vaadin:list' },
  title: 'veterinarians',
};

export default function VetsView() {
  const name = useSignal('');
    const items = useSignal<Vet[]>([]);
    useEffect(() => {
        findAllVets().then((value) => {
            items.value =  value;
        })
    }, []);
  return (
      <>
          <VerticalLayout theme="padding spacing"
                          className="w-full items-center justify-center">
              <h2>Veterinarians</h2>
              <Grid items={items.value}>
                  <GridColumn path="firstName" />
                  <GridColumn path="lastName" />
                  <GridColumn path="specialties" />
              </Grid>
          </VerticalLayout>
      </>
  );
}
