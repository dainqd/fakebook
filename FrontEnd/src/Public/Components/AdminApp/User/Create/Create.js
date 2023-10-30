import React from 'react'
import Header from '../../../Shared/Admin/Header/Header'
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar'
import { Button, Form, Input, message } from 'antd'
import { DatePicker, Space } from 'antd';
import {Link, useNavigate} from 'react-router-dom'
import accountService from '../../../Service/AccountService';
import Footer from "../../../Shared/Admin/Footer/Footer";

function Create() {
  const navigate = useNavigate();

  const onFinish = async (values) => {
    var birthday = document.getElementById("birthday").value;
    var gender = document.getElementById("gender").value;
    var role = document.getElementById("role").value;
    var status = document.getElementById("status").value;

    let data = {
      avatar:"",
      firstName: values.firstName,
      lastName: values.lastName,
      username: values.username,
      email: values.email,
      phoneNumber: values.phoneNumber,
      birthday: birthday,
      gender: gender,
      address: values.address,
      status: status,
      role: role,
      password: values.password,
      confirmPassword: values.confirmPassword
    }

    console.log(data)
    await accountService.adminCreateAccount(data)
        .then((res) => {
          console.log("create account", res.data)
          message.success("Create account success")
          navigate("/account/list")
        })
        .catch((err) => {
          console.log(err)
        })
  };

  const Gender = [
    {
      id: "MALE",
      gender: "MALE"
    },
    {
      id: "FEMALE",
      gender: "FEMALE"
    },
    {
      id: "OTHER",
      gender: "OTHER"
    },
  ]

  const RoleAccount = [
    // {
    //   id:  "ADMIN",
    //   type: "ADMIN"
    // },
    {
      id: "USER",
      type: "USER"
    }
  ]

  const StatusAccount = [
    {
      id:  "ACTIVE",
      type: "ACTIVE"
    },
    {
      id: "INACTIVE",
      type: "INACTIVE"
    }
  ]

  const onChange = (date, dateString) => {
    console.log(date, dateString);
  };


  return (
      <>
        <Header />
        <Sidebar />
        <main id="main" className="main" style={{backgroundColor:"#f6f9ff"}}>
          <div className="pagetitle">
            <h1>Create Account</h1>
            <nav>
              <ol className="breadcrumb">
                <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                <li className="breadcrumb-item">Account</li>
                <li className="breadcrumb-item active">Create Account</li>
              </ol>
            </nav>
          </div>
          <section className="section">
            <div className="row">
              <div className="col-lg-12">
                <div className="card">
                  <div className="card-body">
                    <h5 className="card-title">Create Account</h5>
                    <Form
                        name="basic"
                        labelCol={{
                          span: 8,
                        }}
                        wrapperCol={{
                          span: 16,
                        }}
                        initialValues={{
                          remember: true,
                        }}
                        onFinish={onFinish}
                        autoComplete="off"
                        className="row">

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">FirstName</label>
                        <Form.Item
                            name="firstName"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your name!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">LastName</label>
                        <Form.Item
                            name="lastName"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your name!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">Username</label>
                        <Form.Item
                            name="username"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your username!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">Email</label>
                        <Form.Item
                            name="email"
                            rules={[
                              {
                                type: 'email',
                                message: 'The input is not valid E-mail!',
                              },
                              {
                                required: true,
                                message: 'Please input your E-mail!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">PhoneNumber</label>
                        <Form.Item
                            name="phoneNumber"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your phone number!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear type='number' style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault04" className="form-label">BirthDay</label>
                        <div>
                          <Space direction="vertical">
                            <DatePicker id="birthday" name='birthday' onChange={onChange} style={{ width: "230%", height: "40px" }} />
                          </Space>
                        </div>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault04" className="form-label">GENDER</label>
                        <select className="form-select" id="gender">
                          {Gender.map((item, index) => (
                              <option key={index}>
                                {item.gender}
                              </option>
                          ))}
                        </select>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault01" className="form-label">Address</label>
                        <Form.Item
                            name="address"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your address!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault04" className="form-label">Status</label>
                        <select className="form-select" id="status">
                          {
                            StatusAccount.map((status, index) => (
                                <option key={index}>
                                  {status.type}
                                </option>
                            ))
                          }
                        </select>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault04" className="form-label">Role</label>
                        <select className="form-select" id="role">
                          {
                            RoleAccount.map((role, index) => (
                                <option key={index}>
                                  {role.type}
                                </option>
                            ))
                          }
                        </select>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault03" className="form-label">Password</label>
                        <Form.Item
                            name="password"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your password!',
                              },
                            ]}
                            hasFeedback
                        >
                          <Input.Password allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label htmlFor="validationDefault03" className="form-label">Password Confirm</label>
                        <Form.Item
                            name="confirmPassword"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your password confirm!',
                              }
                            ]}
                            hasFeedback
                        >
                          <Input.Password allowClear style={{ width: "140%", height: "40px" }} />
                        </Form.Item>
                      </div>

                      <div className="col-12">
                        <Form.Item>
                          <Button htmlType="submit" type="primary">Submit</Button>
                        </Form.Item>
                      </div>
                    </Form>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
        <Footer />
      </>
  )
}

export default Create