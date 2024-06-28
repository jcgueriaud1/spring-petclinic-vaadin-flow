import { createMenuItems, useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { AppLayout, DrawerToggle, Icon, SideNav, SideNavItem } from '@vaadin/react-components';
import { Suspense, useEffect } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import {HorizontalLayout} from "@vaadin/react-components/HorizontalLayout.js";
import '@vaadin/icons';
import {translate} from "@vaadin/hilla-react-i18n";

const defaultTitle = document.title;

export default function MainLayout() {
  const currentTitle = (useViewConfig()?.title) ? (translate(useViewConfig()?.title!)+ " - Spring PetClinic"):  defaultTitle;
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    document.title = currentTitle;
  }, [currentTitle]);

  return (
      <AppLayout>
          <header>
              <HorizontalLayout slot="navbar" theme="dark padding" id="header" className="w-full items-center justify-between">
                  <a href="/" className="navbar-brand"><span>Home</span></a>
                  <SideNav className="side-nav-top" onNavigate={({path}) => navigate(path!)}
                           location={location}>
                      {createMenuItems().map(({to, title, icon}) => (
                          <SideNavItem path={to} key={to}>
                              {icon ?
                                  <Icon icon={icon} slot="prefix"></Icon> : <></>}
                              {translate(title!)}
                          </SideNavItem>
                      ))}
                  </SideNav>
              </HorizontalLayout>
          </header>
          <main>
              <Suspense>
                  <div style={{display: 'contents'}}><Outlet/></div>
              </Suspense>
          </main>
          <footer className="footer">
              <img src="./images/vaadin.png" alt="Vaadin"/>
              <img src="./images/spring-logo.svg" alt="Spring"/>
          </footer>
      </AppLayout>
  );
}
