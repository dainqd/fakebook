import React from 'react'
import Header from '../../Shared/Header/Header'
import Sidebar from '../../Shared/Sidebar/Sidebar'
import { Button, Form, Input, message } from 'antd'
import { DatePicker, Space } from 'antd';
import { useNavigate } from 'react-router-dom'
import accountService from '../../Service/AccountService';

function Create() {


  const navigate = useNavigate();

  const onFinish = async (values) => {
    let data = {
      username: values.username,
      email: values.email,
      phoneNumber: values.phoneNumber,
      gender: values.gender,
      birthDay: values.birthDay,
      password: values.password
    }
    await accountService.registerAccount(data)
      .then((res) => {
        console.log("create account", res.data)
        message.success("Create account success")
        navigate("/admin/account/list")
      })
      .catch((err) => {
        console.log(err)
      })
  };

  const Gender = [
    {
      id: 1,
      gender: "MALE"
    },
    {
      id: 2,
      gender: "FEMALE"
    },
    {
      id: 3,
      gender: "ORTHER"
    },
  ]

  const RoleAccount = [
    {
      id: 1,
      type: "ADMIN"
    },
    {
      id: 2,
      type: "USER"
    }
  ]

  const onChange = (date, dateString) => {
    console.log(date, dateString);
  };


  return (
    <>
      <Header />
      <Sidebar />
      <main id="main" className="main">
        <div className="pagetitle">
          <h1>Create Account</h1>
        </div>
        <section className="section">
          <div className="row">
            <div className="col-lg-12">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Create Account</h5>
                  <p>Browser default validation with using the <code>required</code> keyword. Try submitting the form below. Depending on your browser and OS, you’ll see a slightly different style of feedback.</p>
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
                      <label htmlFor="validationDefault01" className="form-label">username</label>
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
                      <label htmlFor="validationDefaultUsername" className="form-label">Email</label>
                      <div className="input-group">
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
                          <Input allowClear style={{ width: "300%", height: "40px" }} />
                        </Form.Item>
                      </div>
                    </div>
                    <div className="col-md-4">
                      <label htmlFor="validationDefault01" className="form-label">Phone Number</label>
                      <Form.Item
                        name="phoneNumber"
                        rules={[
                          {
                            required: true,
                            message: 'Please input your Phone Number!',
                          },
                        ]}
                        hasFeedback
                      >
                        <Input allowClear type='number' style={{ width: "140%", height: "40px" }} />
                      </Form.Item>
                    </div>
                    <div className="col-md-3">
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
                    <div className="col-md-3">
                      <label htmlFor="validationDefault04" className="form-label">BirthDay</label>
                      <div>
                        <Space direction="vertical">
                          <DatePicker name='birthDay' onChange={onChange} style={{ width: "230%", height: "40px" }} />
                        </Space>
                      </div>
                    </div>
                    <div className="col-md-3">
                      <label htmlFor="validationDefault04" className="form-label">Role</label>
                      <select className="form-select" id="validationDefault04">
                        {
                          RoleAccount.map((role, index) => (
                            <option key={index}>
                              {role.type}
                            </option>
                          ))
                        }
                      </select>
                    </div>
                    <div className="col-md-3">
                      <label htmlFor="validationDefault04" className="form-label">GENDER</label>
                      <select className="form-select" id="validationDefault05" >
                        {Gender.map((item, index) => (
                          <option key={index}>
                            {item.gender}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="col-12">
                      <Form.Item>
                        <Button htmlType="submit" type="primary">Submit form</Button>
                      </Form.Item>
                    </div>
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

export default Create
