import { Button, Form, Input, message } from 'antd';
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../../Service/AuthService';

function Register() {

    const navigate = useNavigate();
    const onFinish = async (values) => {
        let data = {
            username: values.username,
            email: values.email,
            password: values.password,
            passwordConfirm: values.passwordConfirm
        }
        await authService.registerAccount(data)
            .then((res) => {
                message.success("Create account success! Login to continue!")
                navigate("/login")
            })
            .catch((err) => {
                console.log(err)
                message.error("Create error")
            })
    };

    return (
        <div className="container" id='register-page'>
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
                                        <h5 className="card-title text-center pb-0 fs-4">Create an Account</h5>
                                        <p className="text-center small">Enter your personal details to create
                                            account</p>
                                    </div>

                                    <Form cclassName="signin-form" onFinish={onFinish}>
                                        <div className="col-12">
                                            <label htmlFor="yourEmail" className="form-label">Your Email</label>
                                            <Form.Item
                                                name="email"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your E-mail!',
                                                    },
                                                    {
                                                        type: "email",
                                                        message: "Invalid E-mail!"
                                                    }
                                                ]}
                                                hasFeedback
                                            >
                                                <Input allowClear  placeholder="Email"  />
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <label htmlFor="yourUsername" className="form-label">Username</label>
                                            <Form.Item
                                                name="username"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your username!' ,
                                                    }
                                                ]}
                                                hasFeedback
                                            >
                                                <Input allowClear type="text" placeholder="Username" />
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
                                                <Input allowClear type="password"  placeholder="Password" />
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <label htmlFor="yourPasswordConfirm" className="form-label">Password Confirm</label>
                                            <Form.Item
                                                name="passwordConfirm"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your password confirm!',
                                                    }
                                                ]}
                                            >
                                                <Input allowClear type="password"  placeholder="Password Confirm" />
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <button className="btn btn-primary w-100" type="submit">Create Account
                                            </button>
                                        </div>
                                        <div className="col-12">
                                            <p className="small mb-0">Already have an account? <Link
                                                to="/login">Log in</Link></p>
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

export default Register
