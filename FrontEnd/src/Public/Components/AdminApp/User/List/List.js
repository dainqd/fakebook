import React, {useEffect, useState} from 'react';
import Header from '../../../Shared/Admin/Header/Header';
import Sidebar from '../../../Shared/Admin/Sidebar/Sidebar';
import {Button, message, Table} from 'antd';
import accountService from '../../../Service/AccountService';
import {Link} from 'react-router-dom';
import Footer from "../../../Shared/Admin/Footer/Footer";
import $ from 'jquery';

function List() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState({
        pagination: {
            current: 1,
            pageSize: 10,
        },
    });

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

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

    const searchUser = async () => {
        $(document).ready(function () {
            $("#inputSearchUser").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $(".ant-table-content table tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    }

    const checkIsDelete = async () => {
        let id = sessionStorage.getItem('id');
        let listBtnDelete = document.getElementsByClassName('btnDelete');
        console.log(listBtnDelete.length)
        for (let i = 0; i < listBtnDelete.length; i++) {
            let item = listBtnDelete[i];
            let value = item.getAttribute('data-id');
            if (value == id) {
                item.disabled = true;
            }
        }
    };

    const handleDelete = async (id) => {
        try {
            if (window.confirm("Delete the user?")) {
                await accountService.adminDeleteAccount(id);
                setData(prevData => prevData.filter(item => item.id !== id));
                message.success(`Deleted account: ${id}`);
            }
        } catch (error) {
            console.error(error);
            message.error('Error deleting account');
        }
    };

    useEffect(() => {
        getListAccount();
        checkIsDelete();
        searchUser();
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
                    <Button className="btnDelete" data-id={id} onClick={() => handleDelete(id)}>
                        Delete
                    </Button>
                    <Button>
                        <Link to={`/account/${id}`}>
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
                    <h1>List Account</h1>
                    <nav>
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
                            <li className="breadcrumb-item">Account</li>
                            <li className="breadcrumb-item active">List Account</li>
                        </ol>
                    </nav>
                </div>
                <div className="row">
                    <div className="mb-3 col-md-3">
                        <h5>Search User</h5>
                        <input className="form-control" id="inputSearchUser" type="text" placeholder="Search.."/>
                        <br/>
                    </div>
                    <Table
                        id="tableUser"
                        style={{margin: "auto"}}
                        columns={columns}
                        dataSource={data}
                        pagination={tableParams.pagination}
                        loading={loading}
                        onChange={handleTableChange}
                    />
                </div>
            </main>
            <Footer/>
        </div>
    );
}

export default List;
