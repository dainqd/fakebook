import { Button, Form, Input, message } from 'antd';
import React, { useEffect, useState } from 'react'
import {Link, useNavigate, useParams} from 'react-router-dom'
import accountService from '../../../Service/AccountService';
import Header from '../../../Shared/Admin/Header/Header';
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar';
import Footer from "../../../Shared/Admin/Footer/Footer";


function Detail() {

  const { id } = useParams();
  const [form] = Form.useForm();
  const navigate = useNavigate();

  useEffect(() => {
    const detailsAccount = async () => {
      await accountService.adminDetailAccount(id)
          .then((res) => {
            console.log("details account", res.data);
            form.setFieldsValue({ id: res.data.id })
            form.setFieldsValue({ username: res.data.username })
            form.setFieldsValue({ firstName: res.data.firstName });
            form.setFieldsValue({ lastName: res.data.lastName })
            form.setFieldsValue({ email: res.data.email });
            form.setFieldsValue({ phoneNumber: res.data.phoneNumber });
            form.setFieldsValue({ birthday: res.data.birthday });
            form.setFieldsValue({ gender: res.data.gender });
            form.setFieldsValue({ status: res.data.status });
            form.setFieldsValue({ role: res.data.role });
          })
          .catch((err) => {
            console.log(err)
          })
    };
    detailsAccount();
  }, [form, id])


  const onFinish = async (values) => {
    let updateData = {
      avatar: "",
      firstName: values.firstName,
      lastName:  values.lastName,
      birthday: values.birthday,
      address: values.address,
      phoneNumber: values.phoneNumber,
      gender: values.gender
    }
    await accountService.adminUpdateAccount(updateData)
        .then((res) => {
          console.log("data", res.data)
          message.success("Update success")
          navigate("/account/list")
        })
        .catch((err) => {
          console.log(err)
          message.error("Update error")
        })
  };

  const gender = [
    {
      id: "MALE",
      "type": "MALE"
    },
    {
      id: "FEMALE",
      "type": "FEMALE"
    },

  ]

  const [value, setValue] = useState({
    gender: ""
  })


  const handleChange = prop => event => {
    setValue({ ...value, [prop]: event.target.value });
  }

  return (
      <>
        <Header />
        <Sidebar />
        <main id="main" className="main" style={{backgroundColor:"#f6f9ff"}}>
          <div className="pagetitle">
            <h1>Detail Account</h1>
            <nav>
              <ol className="breadcrumb">
                <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                <li className="breadcrumb-item">Account</li>
                <li className="breadcrumb-item active">Detail Account</li>
              </ol>
            </nav>
          </div>{/* End Page Title */}
          <section className="section">
            <div className="row">
              <div className="col-lg-12">
                <div className="card">
                  <div className="card-body">
                    <h5 className="card-title">Detail Account</h5>
                    <Form className="row"
                          form={form}
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
                    >
                      <div className="col-md-2">
                        <label>
                          ID
                        </label>
                        <Form.Item
                            name="id">
                          <Input disabled />
                        </Form.Item>
                      </div>

                      <div className="col-md-3">
                        <label>
                          USERNAME
                        </label>
                        <Form.Item
                            name="username">
                          <Input disabled />
                        </Form.Item>
                      </div>

                      <div className="col-md-3">
                        <label>
                          FIRSTNAME
                        </label>
                        <Form.Item
                            name="firstName"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your name!',
                              },
                            ]}
                        >
                          <Input />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label>
                          LASTNAME
                        </label>
                        <Form.Item
                            name="lastName"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your name!',
                              },
                            ]}>
                          <Input />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label>
                          EMAIL
                        </label>
                        <Form.Item name="email">
                          <Input disabled/>
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label>
                          PHONENUMBER
                        </label>
                        <Form.Item
                            name="phoneNumber"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your phone!',
                              },
                            ]}
                        >
                          <Input />
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label>
                          BIRTHDAY
                        </label>
                        <Form.Item
                            name="birthday"
                            rules={[
                              {
                                required: true,
                                message: 'Please input your name!',
                              },
                            ]}>
                          <Input type='data'/>
                        </Form.Item>
                      </div>

                      <div className="col-md-4">
                        <label>
                          Gender
                        </label>
                        <div>
                          <select className="form-select">
                            {
                              gender.map((item, index) => (
                                  <option key={index} value={value.gender} onChange={handleChange("gender")}>
                                    {item.id}
                                  </option>
                              ))
                            }
                          </select>
                        </div>

                        {/* <Form.Item
                                                name="gender" rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your gender!',
                                                    },
                                                ]}>
                                                <Input />
                                            </Form.Item> */}
                      </div>
                      <div className="col-md-4">
                        <label>
                          ROLE
                        </label>
                        <Form.Item
                            name="role">
                          <Input disabled />
                        </Form.Item>
                      </div>
                      <div className="col-md-4">
                        <label>
                          STATUS
                        </label>
                        <Form.Item
                            name="status">
                          <Input disabled />
                        </Form.Item>
                      </div>
                      <Form.Item
                          wrapperCol={{
                            offset: 8,
                            span: 16,
                          }}
                      >
                        <Button type="primary" htmlType="submit">
                          Submit
                        </Button>
                      </Form.Item>
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

export default Detail
