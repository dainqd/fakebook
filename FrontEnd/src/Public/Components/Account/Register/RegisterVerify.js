import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {Button, Form, Input, message} from 'antd';
import authService from '../../Service/AuthService';


function RegisterVerify() {
    const navigate = useNavigate();

    const onFinish = async (values) => {
        let data = {
            username: localStorage.getItem('username'),
            verifyCode: values.verifyCode
        }
        await authService.registerVerify(data)
            .then((res) => {
                localStorage.removeItem('username')
                localStorage.removeItem('email')
                message.success("Register success! Please login to continue!")
                navigate("/login")
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
                                    <span className="d-none d-lg-block">NiceAdmin</span>
                                </Link>
                            </div>

                            <div className="card mb-3">

                                <div className="card-body">

                                    <div className="pt-4 pb-2">
                                        <h5 className="card-title text-center pb-0 fs-4">Verify Your Account</h5>
                                    </div>

                                    <Form onFinish={onFinish}
                                          autoComplete="off" className="row g-3 needs-validation" noValidate>

                                        <div className="col-12">
                                            <label htmlFor="verifyCode" className="form-label">Verify Code</label>
                                            <Form.Item
                                                name="verifyCode"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your verifyCode!',
                                                    }
                                                ]}
                                                hasFeedback
                                            >
                                                <Input allowClear type="text" placeholder="VerifyCode"/>
                                            </Form.Item>
                                        </div>

                                        <div className="col-12">
                                            <button className="btn btn-primary w-100" type="submit">Submit</button>
                                        </div>
                                        <div className="col-12">
                                            <p className="small mb-0">Already have an account? <Link
                                                to="/login">Log in</Link></p>
                                        </div>
                                    </Form>

                                </div>
                            </div>

                            <div className="credits">
                                Designed by <a href="/">BootstrapMade</a>
                            </div>

                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default RegisterVerify
