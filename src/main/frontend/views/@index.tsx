import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button } from '@vaadin/react-components/Button.js';
import { Notification } from '@vaadin/react-components/Notification.js';
import { TextField } from '@vaadin/react-components/TextField.js';
import {VerticalLayout} from "@vaadin/react-components";
import {translate} from "@vaadin/hilla-react-i18n";

export const config: ViewConfig = {
  menu: { order: 0, icon: 'vaadin:home' },
  title: 'home',
};

export default function HelloWorldView() {
  const name = useSignal('');

  return (
      <>
          <VerticalLayout theme="padding spacing"
                          className="w-full items-center justify-center">
              <h2>{translate('welcome')}</h2>
              <img src="./images/pets.png" alt={translate('pets')}/>
          </VerticalLayout>
      </>
  );
}
