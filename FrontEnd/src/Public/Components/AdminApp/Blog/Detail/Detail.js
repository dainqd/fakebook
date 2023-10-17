import {Button, Form, Input, message} from 'antd';
import React, {useEffect, useState} from 'react'
import {Link, useNavigate, useParams} from 'react-router-dom'
import blogService from '../../../Service/BlogService';
import Header from '../../../Shared/Admin/Header/Header';
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar';
import Footer from "../../../Shared/Admin/Footer/Footer";
import TextArea from "antd/lib/input/TextArea";
import $ from 'jquery';
import Modal from "react-bootstrap/Modal";
import axios from "axios";

function uploadImageMain(idInput) {
    return new Promise(function (resolve, reject) {
        const urlUpload = 'http://127.0.0.1:8000/upload-image';
        const formData = new FormData();
        const photo = document.getElementById(idInput).files[0];

        formData.append('thumbnail', photo);

        fetch(urlUpload, {
            method: 'POST',
            body: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(response => response.json())
            .then(data => {
                resolve(data);
            })
            .catch(error => {
                reject(error);
            });
    });
}


function Detail() {

    const {id} = useParams();
    const [form] = Form.useForm();
    const navigate = useNavigate();

    const detailsBlog = async () => {
        await blogService.adminDetailBlog(id)
            .then((res) => {
                console.log("details blog", res.data);
                form.setFieldsValue({id: res.data.id})
                form.setFieldsValue({content: res.data.content})
                form.setFieldsValue({thumbnail: res.data.thumbnail});
                form.setFieldsValue({status: res.data.status});

                let imgUrl = res.data.thumbnail;
                $('#uploadImg').attr("src", imgUrl).css('width', '80px').css('height', '80px');
            })
            .catch((err) => {
                console.log(err)
            })
    };

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const setImg = async () => {
        let imgUrl = $('#thumbnail').val();
        console.log(imgUrl);
    };

    useEffect(() => {
        detailsBlog();
        setImg();
    }, [form, id])


    const handleUpload = async (e) => {
        e.preventDefault();
        const inputFile = document.getElementById('inputFile');
        const formData = new FormData();
        formData.append('thumbnail', inputFile.files[0]);

        try {
            const response = await axios.post('http://127.0.0.1:8000/upload-image', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const onFinish = async (values) => {
        let updateData = {
            content: values.content,
            thumbnail: values.thumbnail,
            status: values.status,
        }
        await blogService.adminUpdateBlog(updateData)
            .then((res) => {
                console.log("data", res.data)
                message.success("Update success")
                navigate("/admin/blog/list")
            })
            .catch((err) => {
                console.log(err)
                message.error("Update error")
            })
    };

    return (
        <>
            <Header/>
            <Sidebar/>
            <main id="main" className="main" style={{backgroundColor: "#f6f9ff"}}>
                <div className="pagetitle">
                    <h1>Detail Blog</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Blog</li>
                            <li className="breadcrumb-item active">Detail Blog</li>
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
                                        <div className="col-md-6">
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
                                            <Form.Item
                                                name="thumbnail"
                                                id="thumbnail"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: 'Please input your thumbnail!',
                                                    },
                                                ]}>
                                                <Input disabled/>
                                            </Form.Item>
                                            <img src="" alt="" id="uploadImg" className="uploadImg"/>
                                        </div>

                                        <div className="col-md-2">
                                            <Button className="mt-4 btn btn-outline-success" type="button"
                                                    htmlType="button"
                                                    onClick={handleShow}>
                                                Upload
                                            </Button>
                                        </div>

                                        <div className="col-md-2">
                                            <label>
                                                STATUS
                                            </label>
                                            <Form.Item
                                                name="status">
                                                <Input/>
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
