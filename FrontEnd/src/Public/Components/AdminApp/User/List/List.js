import React, { useEffect, useState } from 'react';
import Header from '../../../Shared/Admin/Header/Header';
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar';
import { Button, Form, message, Table } from 'antd';
import accountService from '../../../Service/AccountService';
import { Link } from 'react-router-dom';
import Footer from "../../../Shared/Admin/Footer/Footer";

// Modal Component
function DeleteAccountModal({ id, handleDelete }) {
    return (
        <div className="modal fade" id={`deleteAccount-${id}`} tabIndex="-1" role="dialog" aria-labelledby={`editModalLabel-${id}`} aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id={`exampleModalLabel-${id}`}>Delete Account</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <h5 className="text-center">Are you sure you want to delete this account?</h5>
                        <Form id={`delete-account-form-${id}`}>
                            <div className="d-flex justify-content-around">
                                <button type="submit" className="btn w-25 btn-danger" onClick={() => handleDelete(id)}>Delete</button>
                                <button type="button" className="btn w-25 btn-secondary" data-dismiss="modal">Back</button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
}

function List() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState({
        pagination: {
            current: 1,
            pageSize: 10,
        },
    });

    const getListAccount = async () => {
        setLoading(true);
        try {
            const res = await accountService.adminListAccount();
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
            await accountService.adminDeleteAccount(id);
            setData(prevData => prevData.filter(item => item.id !== id));
            message.success(`Deleted account: ${id}`);
        } catch (error) {
            console.error(error);
            message.error('Error deleting account');
        }
    };

    useEffect(() => {
        getListAccount();
    }, []);

    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            width: '10%',
        },
        {
            title: 'FirstName',
            dataIndex: 'firstName',
            width: '10%',
        },
        {
            title: 'LastName',
            dataIndex: 'lastName',
            width: '10%',
        },
        {
            title: 'Username',
            dataIndex: 'username',
            width: '10%',
        },
        {
            title: 'Email',
            dataIndex: 'email',
        },
        {
            title: 'Status',
            dataIndex: 'status',
        },
        {
            title: 'Role',
            dataIndex: 'role',
        },
        {
            title: 'Phone Number',
            dataIndex: 'phoneNumber',
        },
        {
            title: 'BirthDay',
            dataIndex: 'birthday',
        },
        {
            title: 'Gender',
            dataIndex: 'gender',
        },
        {
            title: 'Action',
            dataIndex: 'id',
            key: 'x',
            render: (id) => (
                <>
                    {/*<Button className="" data-toggle="modal" data-target={`#deleteAccount-${id}`}>*/}
                    <Button onClick={() => handleDelete(id)}>
                        Delete
                    </Button>
                    <Button>
                        <Link to={`/account/${id}`}>
                            Details
                        </Link>
                    </Button>
                    {/*<DeleteAccountModal id={id} handleDelete={handleDelete} />*/}
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
            <Header />
            <Sidebar />

            <main id="main" className="main" style={{ backgroundColor: "#f6f9ff" }}>
                <div className="pagetitle">
                    <h1>List Account</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Account</li>
                            <li className="breadcrumb-item active">List Account</li>
                        </ol>
                    </nav>
                </div>
                <Table
                    style={{ margin: "auto" }}
                    columns={columns}
                    dataSource={data}
                    pagination={tableParams.pagination}
                    loading={loading}
                    onChange={handleTableChange}
                />
            </main>
            <Footer />
        </div>
    );
}

export default List;
