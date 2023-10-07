import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {Button, Form, Input, message} from 'antd';
import authService from '../../Service/AuthService';


function Login() {
    const navigate = useNavigate();

    const onFinish = async (values) => {
        let data = {
            username: values.username,
            password: values.password
        }
        await authService.loginAccount(data)
            .then((res) => {
                localStorage.clear();
                sessionStorage.setItem("accessToken", res.data.token);
                sessionStorage.setItem("id", res.data.id);
                sessionStorage.setItem("username", res.data.username);
                message.success(`Welcome ${res.data.username} !`)
                let roles = res.data.roles;
                let isAdmin = false;
                for (let i = 0; i < roles.length; i++) {
                    if (roles[i] == 'ADMIN') {
                        isAdmin = true;
                    }
                }
                if (isAdmin) {
                    navigate('/dashboard')
                } else {
                    navigate('/profile')
                }

            })
            .catch((err) => {
                console.log(err)
                message.error("Please check account!")
            })
    };

    return (
        <div className="container" id='login-form'>

            <section
                className="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

                            <div className="d-flex justify-content-center py-4">
                                <Link to="/" className="logo d-flex align-items-center w-auto">
                                    <img src="assets/img/logo.png" alt=""></img>
                                    <span className="d-none d-lg-block">FaceBook</span>
                                </Link>
                            </div>

                            <div className="card mb-3">

                                <div className="card-body">

                                    <div className="pt-4 pb-2">
                                        <h5 className="card-title text-center pb-0 fs-4">Login to Your Account</h5>
                                        <p className="text-center small">Enter your username & password to login</p>
                                    </div>

                                    <Form onFinish={onFinish}
                                          autoComplete="off" className="row g-3 needs-validation" noValidate>

                                        <div className="col-12">
                                            <label htmlFor="yourUsername" className="form-label">Username</label>
                                            <Form.Item
                                                name="username"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your username!',
                                                    }
                                                ]}
                                                hasFeedback
                                            >
                                                <Input allowClear type="text" placeholder="Username"/>
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <label htmlFor="yourPassword" className="form-label">Password</label>
                                            <Form.Item
                                                name="password"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your password!',
                                                    }
                                                ]}
                                            >
                                                <Input allowClear type="password" placeholder="Password"/>
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <button className="btn btn-primary w-100" type="submit">Login</button>
                                        </div>
                                        <div className="col-12">
                                            <p className="small mb-0">Don't have account? <Link to="/register">Create
                                                an account</Link></p>
                                        </div>
                                    </Form>

                                </div>
                            </div>

                            <div className="credits">
                                Designed by <a href="/">FakeBook Team</a>
                            </div>

                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default Login
