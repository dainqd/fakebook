import React, {useEffect} from 'react'
import Header from '../../../Shared/Admin/Header/Header'
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar'
import {Button, Form, message} from 'antd'
import {Link, useNavigate} from 'react-router-dom'
import marketingService from '../../../Service/MarketingService';
import Footer from "../../../Shared/Admin/Footer/Footer";
import accountService from "../../../Service/AccountService";
import $ from "jquery";
import uploadImageMain from "../../../../Main/Main";

function MarketingCreate() {
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

    const uploadImage = async () => {
        await $('#thumbnailCreateMarketing').on('change', function () {
            uploadImageMain('thumbnailCreateMarketing').then(function (response) {
                $('#modalImageShow').attr("src", response).removeClass('d-none').height(150).width(150);
                console.log(response);
                $('#thumbnailCreateMarketingMain').val(response);
            }).catch(function (error) {
                console.error('Error:', error);
            });
        });
    }


    const onFinish = async (values) => {
        var content = document.getElementById("content").value;
        var status = document.getElementById("status").value;
        var thumbnail = document.getElementById("thumbnailCreateMarketingMain").value;

        var duration = document.getElementById("duration").value;
        var endDate = document.getElementById("endDate").value;
        var startDate = document.getElementById("startDate").value;

        let item = await getUser();

        let user = {
            id: item.id
        };

        let data = {
            user: user,
            content: content,
            duration: duration,
            endDate: endDate,
            startDate: startDate,
            thumbnail: thumbnail,
            status: status
        }

        await marketingService.adminCreateMarketing(data)
            .then((res) => {
                console.log("create marketing", res.data)
                message.success("Create marketing success")
                navigate("/admin/marketing/list")
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

    useEffect(() => {
        uploadImage();
    }, []);

    return (
        <>
            <Header/>
            <Sidebar/>
            <main id="main" className="main" style={{backgroundColor: "#f6f9ff"}}>
                <div className="pagetitle">
                    <h1>Create Marketing</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Marketing</li>
                            <li className="breadcrumb-item active">Create Marketing</li>
                        </ol>
                    </nav>
                </div>
                <section className="section">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">Create Marketing</h5>
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
                                            <label htmlFor="startDate" className="form-label">Start Date</label>
                                            <input type="datetime-local" className="form-control" id="startDate"/>
                                        </div>
                                        <div className="col-md-4">
                                            <label htmlFor="endDate" className="form-label">End Date</label>
                                            <input type="datetime-local" className="form-control" id="endDate"/>
                                        </div>
                                        <div className="col-md-4">
                                            <label htmlFor="duration" className="form-label">Duration(Hours)</label>
                                            <input type="number" className="form-control" id="duration" min="0"/>
                                        </div>

                                        <div className="col-md-6">
                                            <label htmlFor="status" className="form-label">Status</label>
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
                                        <div className="col-md-6">
                                            <label htmlFor="thumbnailCreateMarketing"
                                                   className="form-label">Thumbnail</label>
                                            <input id="thumbnailCreateMarketing" type="file" multiple
                                                   className="form-control"/>
                                            <input id="thumbnailCreateMarketingMain" type="text" className="d-none"/>
                                            <img src="" alt="" id="modalImageShow" className="d-none"/>
                                        </div>

                                        <div className="col-md-12">
                                            <label htmlFor="content" className="form-label">Content</label>
                                            <textarea id="content" name="content"
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

export default MarketingCreate