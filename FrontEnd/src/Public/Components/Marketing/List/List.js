import React, {useEffect, useState} from 'react';
import Header from '../../Shared/Admin/Header/Header';
import Sidebar from '../../Shared/Client/Sidebar/Sidebar';
import {Button, Form, message, Table} from 'antd';
import blogService from '../../Service/BlogService';
import {Link} from 'react-router-dom';
import Footer from "../../Shared/Admin/Footer/Footer";

function List() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState({
        pagination: {
            current: 1,
            pageSize: 10,
        },
    });

    const getListBlog = async () => {
        setLoading(true);
        let data = {
            page: 0,
            size: 100,
            status: ''
        }

        try {
            const res = await blogService.listBlogByUser(sessionStorage.getItem('id'), data);
            if (res.status === 200) {
                setData(res.data.content);
            } else {
                message.error('Error');
            }
        } catch (err) {
            console.error(err);
            message.error('Error');
        } finally {
            setLoading(false);
        }
    }

    const handleDelete = async (id) => {
        try {
            if (window.confirm("Delete the blog?")) {
                await blogService.deleteBlog(id);
                setData(prevData => prevData.filter(item => item.id !== id));
                message.success(`Deleted blog: ${id}`);
            }
        } catch (error) {
            console.error(error);
            message.error('Error deleting account');
        }
    };

    useEffect(() => {
        getListBlog();
    }, []);

    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            width: '10%',
        },
        {
            title: 'Content',
            dataIndex: 'content',
            width: '50%',
        },
        {
            title: 'Status',
            dataIndex: 'status',
            width: '10%',
        },
        {
            title: 'Action',
            dataIndex: 'id',
            key: 'x',
            render: (id) => (
                <>
                    <Button onClick={() => handleDelete(id)}>
                        Delete
                    </Button>
                    <Button>
                        <Link to={`/blog/${id}`}>
                            Details
                        </Link>
                    </Button>
                </>
            ),
        },
    ];

    const handleTableChange = (pagination, filters, sorter) => {
        setTableParams({
            pagination,
            filters,
            ...sorter,
        });
    };

    return (
        <div>
            <Header/>
            <Sidebar/>

            <main id="main" className="main" style={{backgroundColor: "#f6f9ff"}}>
                <div className="pagetitle">
                    <h1>List Blog</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Blog</li>
                            <li className="breadcrumb-item active">List Blog</li>
                        </ol>
                    </nav>
                </div>
                <Table
                    style={{margin: "auto"}}
                    columns={columns}
                    dataSource={data}
                    pagination={tableParams.pagination}
                    loading={loading}
                    onChange={handleTableChange}
                />
            </main>
            <Footer/>
        </div>
    );
}

export default List;
