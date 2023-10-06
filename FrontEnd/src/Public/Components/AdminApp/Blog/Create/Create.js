import React, {useEffect, useState} from 'react'
import Header from '../../../Shared/Admin/Header/Header'
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar'
import {Button, Form, Input, message} from 'antd'
import {DatePicker, Space} from 'antd';
import {Link, useNavigate} from 'react-router-dom'
import blogService from '../../../Service/BlogService';
import Footer from "../../../Shared/Admin/Footer/Footer";
import accountService from "../../../Service/AccountService";

function BlogCreate() {
    const navigate = useNavigate();
    const AuthName = sessionStorage.getItem("username");

    var userCreate;

    async function getUser() {
        await accountService.findUserByUsername(AuthName)
            .then((res) => {
                if (res.status === 200) {
                    userCreate = res.data;
                }
            })
        return userCreate;
    }



    const onFinish = async (values) => {
        var content = document.getElementById("content").value;
        var status = document.getElementById("status").value;

        let item = await getUser();

        let user = {
            id: item.id
        };

        let data = {
            user_id: user,
            content: content,
            status: status
        }

        console.log(data)

        await blogService.adminCreateBlog(data)
            .then((res) => {
                console.log("create blog", res.data)
                message.success("Create blog success")
                navigate("/blog/list")
            })
            .catch((err) => {
                console.log(err)
            })
    };

    const StatusAccount = [
        {
            id: "ACTIVE",
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
            <Header/>
            <Sidebar/>
            <main id="main" className="main" style={{backgroundColor: "#f6f9ff"}}>
                <div className="pagetitle">
                    <h1>Create Blog</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Blog</li>
                            <li className="breadcrumb-item active">Create Blog</li>
                        </ol>
                    </nav>
                </div>
                <section className="section">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">Create Blog</h5>
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

                                        <div className="col-md-6">
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

                                        <div className="col-md-12">
                                            <label htmlFor="validationDefault02" className="form-label">LastName</label>
                                            <textarea id="content" name="validationDefault02"
                                                      className="form-control"></textarea>
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
            <Footer/>
        </>
    )
}

export default BlogCreate