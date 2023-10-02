import { Button, Form, Input, message } from 'antd';
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import accountService from '../../Service/AccountService';
import Header from '../../Shared/Header/Header';
import Sidebar from '../../Shared/Sidebar/Sidebar';


function Detail() {

  const { id } = useParams();
  const [form] = Form.useForm();
  const navigate = useNavigate();

  useEffect(() => {
    const detailsAccount = async () => {
      await accountService.detailAccount(id)
        .then((res) => {
          console.log("details account", res.data);
          form.setFieldsValue({ id: res.data.id });
          form.setFieldsValue({ username: res.data.username })
          form.setFieldsValue({ email: res.data.email });
          form.setFieldsValue({ phoneNumber: res.data.phoneNumber });
          form.setFieldsValue({ gender: res.data.gender });
        })
        .catch((err) => {
          console.log(err)
        })
    };
    detailsAccount();
  }, [])


  const onFinish = async (values) => {
    let updateData = {
      id: values.id,
      username: values.username,
      email: values.email,
      phoneNumber: values.phoneNumber,
      gender: values.gender
    }
    await accountService.updateAccount(values.id, updateData)
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
      id: 1,
      "type": "MALE"
    },
    {
      id: 2,
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
      <main id="main" className="main">
        <div className="pagetitle">
          <h1>Detail Account</h1>
        </div>
        <section className="section">
          <div className="row">
            <div className="col-lg-12">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Detail Account</h5>
                  <p>Browser default validation with using the <code>required</code> keyword. Try submitting the form below. Depending on your browser and OS, you’ll see a slightly different style of feedback.</p>
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
                    <div className="col-md-4">
                      <label>
                        ID
                      </label>
                      <Form.Item
                        name="id">
                        <Input disabled />
                      </Form.Item>
                    </div>
                    <div className="col-md-4">
                      <label>
                        USERNAME
                      </label>
                      <Form.Item
                        name="username"
                        rules={[
                          {
                            required: true,
                            message: 'Please input your username!',
                          },
                        ]}
                      >
                        <Input />
                      </Form.Item>
                    </div>
                    <div className="col-md-4">
                      <label>
                        Email
                      </label>
                      <Form.Item
                        name="email"
                        rules={[
                          {
                            required: true,
                            message: 'Please input your Email!',
                          },
                        ]}>
                        <Input />
                      </Form.Item>
                    </div>
                    <div className="col-md-4">
                      <label>
                        Phone Number
                      </label>
                      <Form.Item
                        name="phoneNumber" rules={[
                          {
                            required: true,
                            message: 'Please input your Phone Number!',
                          },
                        ]}>
                        <Input />
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
    </>
  )
}

export default Detail
