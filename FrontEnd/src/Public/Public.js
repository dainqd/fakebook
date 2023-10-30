import React from 'react'
import {Route, Routes} from 'react-router-dom'
import Login from './Components/Account/Login/Login'
import Register from './Components/Account/Register/Register'
import RegisterVerify from './Components/Account/Register/RegisterVerify'
import Dasboard from './Components/AdminApp/Dasboard/Dasboard'
import Home from './Components/Home/Home'
import Profile from './Components/Profile/Profile'
import Error404 from './Components/Shared/Error/Error404'
import Create from './Components/AdminApp/User/Create/Create'
import Detail from './Components/AdminApp/User/Detail/Detail'
import List from './Components/AdminApp/User/List/List'

import BlogCreate from './Components/AdminApp/Blog/Create/Create'
import BlogList from './Components/AdminApp/Blog/List/List'
import BlogDetail from './Components/AdminApp/Blog/Detail/Detail'

import ClientBlogCreate from './Components/Blog/Create/Create'
import ClientBlogList from './Components/Blog/List/List'
import ClientBlogDetail from './Components/Blog/Detail/Detail'

import MarketingCreate from './Components/AdminApp/Marketing/Create/Create'
import MarketingList from './Components/AdminApp/Marketing/List/List'
import MarketingDetail from './Components/AdminApp/Marketing/Detail/Detail'

import ClientMarketingCreate from './Components/Marketing/Create/Create'
import ClientMarketingList from './Components/Marketing/List/List'
import ClientMarketingDetail from './Components/Marketing/Detail/Detail'

function Public() {
    return (
        <div>
            <Routes>
                <Route path='/' element={<Home/>}/>
                <Route path='/dashboard' element={<Dasboard/>}/>
                <Route path='/login' element={<Login/>}/>
                <Route path='/register' element={<Register/>}/>
                <Route path='/register-verify' element={<RegisterVerify/>}/>
                <Route path='/account/list' element={<List/>}/>
                <Route path='/account/create' element={<Create/>}/>
                <Route path='/account/:id' element={<Detail/>}/>
                <Route path='/*' element={<Error404/>}/>
                <Route path='/profile' element={<Profile/>}/>
                {/*blog*/}
                <Route path='/marketing/list' element={<ClientMarketingList/>}/>
                <Route path='/marketing/create' element={<ClientMarketingCreate/>}/>
                <Route path='/marketing/:id' element={<ClientMarketingDetail/>}/>
                {/*blog*/}
                <Route path='/blog/list' element={<ClientBlogList/>}/>
                <Route path='/blog/create' element={<ClientBlogCreate/>}/>
                <Route path='/blog/:id' element={<ClientBlogDetail/>}/>
                {/*<Route path='/admin/blog/list' element={<BlogCreate />} />*/}
                <Route path='/admin/blog/list' element={<BlogList/>}/>
                <Route path='/admin/blog/create' element={<BlogCreate/>}/>
                <Route path='/admin/blog/:id' element={<BlogDetail/>}/>
                {/*    */}
                <Route path='/admin/marketing/list' element={<MarketingList/>}/>
                <Route path='/admin/marketing/create' element={<MarketingCreate/>}/>
                <Route path='/admin/marketing/:id' element={<MarketingDetail/>}/>
            </Routes>
        </div>
    )
}

export default Public
