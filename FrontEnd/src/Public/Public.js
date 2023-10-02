import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from './Components/Account/Login/Login'
import Register from './Components/Account/Register/Register'
import RegisterVerify from './Components/Account/Register/RegisterVerify'
import Dasboard from './Components/Dasboard/Dasboard'
import Home from './Components/Home/Home'
import Profile from './Components/Profile/Profile'
import Error404 from './Components/Shared/Error/Error404'
import Create from './Components/User/Create/Create'
import Detail from './Components/User/Detail/Detail'
import List from './Components/User/List/List'

function Public() {
    return (
        <div>
            <Routes >
                <Route path='/' element={<Home />} />
                <Route path='/dashboard' element={<Dasboard />} />
                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />
                <Route path='/register-verify' element={<RegisterVerify />} />
                <Route path='/account/list' element={<List />} />
                <Route path='/account/create' element={<Create />} />
                <Route path='/account/:id' element={<Detail />} />
                <Route path='/*' element={<Error404 />} />
                <Route path='/profile' element={<Profile />} />
            </Routes>
        </div>
    )
}

export default Public
