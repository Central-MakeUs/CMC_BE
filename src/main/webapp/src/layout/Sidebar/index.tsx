import React from 'react';
import {useDispatch} from 'react-redux';
import {CImage, CSidebar, CSidebarBrand, CSidebarNav, CSidebarToggler} from '@coreui/react';
import {SidebarNav} from '../SidebarNav';
import SimpleBar from 'simplebar-react';

// import 'simplebar/dist/simplebar.min.css'
// sidebar nav config
import navigation from '../../config/_nav';
import {useTypedSelector} from '../../config/store';

const Sidebar = () => {
    const dispatch = useDispatch();
    const unfoldable = useTypedSelector(state => state.sidebarUnfoldable);
    const sidebarShow = useTypedSelector(state => state.sidebarShow);

    return (
        <CSidebar
            position='fixed'
            unfoldable={unfoldable}
            visible={sidebarShow}
            onVisibleChange={visible => {
                dispatch({type: 'set', sidebarShow: visible});
            }}
        >
            <CSidebarBrand className='d-none d-md-flex'>
                <CImage src={`${process.env.PUBLIC_URL}/logo.png`}/>
                {/*<CImage*/}
                {/*  className='sidebar-brand-full'*/}
                {/*  src={`${process.env.PUBLIC_URL}/neordinary-logo.svg`}*/}
                {/*  alt='neordinary-logo'*/}
                {/*/>*/}
            </CSidebarBrand>
            <CSidebarNav>
                <SimpleBar>
                    {/*eslint-disable-next-line @typescript-eslint/ban-ts-comment*/}
                    {/*@ts-ignore*/}
                    <SidebarNav items={navigation}/>
                </SimpleBar>
            </CSidebarNav>
            <CSidebarToggler
                className='d-none d-lg-flex'
                onClick={() => dispatch({type: 'set', sidebarUnfoldable: !unfoldable})}
            />
        </CSidebar>
    );
};

export default React.memo(Sidebar);
