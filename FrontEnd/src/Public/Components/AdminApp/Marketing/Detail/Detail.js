import {Button, Form, Input, message} from 'antd';
import React, {useEffect, useState} from 'react'
import {Link, useNavigate, useParams} from 'react-router-dom'
import marketingService from '../../../Service/MarketingService';
import Header from '../../../Shared/Admin/Header/Header';
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar'
import Footer from "../../../Shared/Admin/Footer/Footer";
import TextArea from "antd/lib/input/TextArea";
import $ from 'jquery';
import Modal from "react-bootstrap/Modal";
import uploadImageMain from "../../../../Main/Main";

function Detail() {

    const {id} = useParams();
    const [form] = Form.useForm();
    const navigate = useNavigate();

    var marketing = null;

    const detailMarketing = async () => {
        await marketingService.adminDetailMarketing(id)
            .then((res) => {
                console.log("details marketing", res.data);
                form.setFieldsValue({content: res.data.content})
                $('#startDate').val(res.data.startDate);
                $('#endDate').val(res.data.endDate);
                $('#duration').val(res.data.duration);
                marketing = res.data;
                let imgUrl = res.data.thumbnail;
                if (marketing.status == 'ACTIVE') {
                    $('#updateStatusMarketing').empty().append(`<option value="ACTIVE">ACTIVE</option>
                                                        <option value="INACTIVE">INACTIVE</option>`);
                } else {
                    $('#updateStatusMarketing').empty().append(`<option value="INACTIVE">INACTIVE</option>
                                                        <option value="ACTIVE">ACTIVE</option>`);
                }
                $('#thumbnailCreateMarketingMain').val(imgUrl);
                $('#uploadImg').attr("src", imgUrl).css('width', '80px').css('height', '80px');
            })
            .catch((err) => {
                console.log(err)
            })
    };

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const uploadImage = async () => {
        await $('#thumbnailCreateMarketing').on('change', function () {
            uploadImageMain('thumbnailCreateMarketing').then(function (response) {
                $('#uploadImg').attr("src", response);
                $('#thumbnailCreateMarketingMain').val(response);
            }).catch(function (error) {
                console.error('Error:', error);
            });
        });
    }

    const handleUpload = async (e) => {
        e.preventDefault();

        try {
            const response = await uploadImageMain('inputFile');
            console.log('Response:', response);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const onFinish = async (values) => {
        let thumbnail = $('#thumbnailCreateMarketingMain').val();
        let status = $('#updateStatusMarketing').val();

        let startDate = $('#startDate').val();
        let endDate = $('#endDate').val();
        let duration = $('#duration').val();

        marketing.content = values.content;
        marketing.thumbnail = thumbnail;
        marketing.status = status;
        marketing.startDate = startDate;
        marketing.endDate = endDate;
        marketing.duration = duration;

        let updateData = marketing;
        await marketingService.adminUpdateMarketing(updateData)
            .then((res) => {
                console.log("data", res.data)
                message.success("Update success")
                navigate("/admin/marketing/list")
            })
            .catch((err) => {
                console.log(err)
                message.error("Update error")
            })
    };

    useEffect(() => {
        detailMarketing();
        uploadImage();
    }, [form, id])

    return (
        <>
            <Header/>
            <Sidebar/>
            <main id="main" className="main" style={{backgroundColor: "#f6f9ff"}}>
                <div className="pagetitle">
                    <h1>Detail Marketing(</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Marketing(</li>
                            <li className="breadcrumb-item active">Detail Marketing(</li>
                        </ol>
                    </nav>
                </div>
                {/* End Page Title */}
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

                                        <div className="col-md-8">
                                            <label>
                                                CONTENT
                                            </label>
                                            <Form.Item
                                                name="content"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your content!',
                                                    },
                                                ]}
                                            >
                                                <TextArea row={4}/>
                                            </Form.Item>
                                        </div>

                                        <div className="col-md-2">
                                            <label>
                                                THUMBNAIL
                                            </label>
                                            <input id="thumbnailCreateMarketing" type="file" multiple
                                                   className="form-control"/>
                                            <input id="thumbnailCreateMarketingMain" type="text" className="d-none"/>
                                            <img src="" alt="" id="uploadImg" className="uploadImg"/>
                                        </div>

                                        <div className="col-md-2">
                                            <label htmlFor="status">Status</label>
                                            <select className="form-select" id="updateStatusMarketing">

                                            </select>
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
                <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Upload Image</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <input className="form-control" type="file" id="inputFile"/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                        <Button className="btn btn-primary" onClick={handleUpload}>
                            Upload
                        </Button>
                    </Modal.Footer>
                </Modal>
            </main>
            <Footer/>
        </>
    )
}

export default Detail
