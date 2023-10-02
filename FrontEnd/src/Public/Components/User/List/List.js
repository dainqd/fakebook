import React, { useEffect, useState } from 'react'
import Header from '../../Shared/Header/Header'
import Sidebar from '../../Shared/Sidebar/Sidebar'
import { Button, message, Table } from 'antd';
import accountService from '../../Service/AccountService';
import { Link } from 'react-router-dom';



function List() {
    const handleDelete = async (id) => {
        await accountService.deleteAccount(id)
            .then((res) => {
                console.log("delete", res.data)
                message.success(`Delete account: ${id}`)
                getListAccount();
            })
            .catch((err) => {
                console.log(err)
            })
    }

    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            width: '10%',
        },
        {
            title: 'Username',
            dataIndex: 'username',
            width: '20%',
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
            render: (id) =>
                <>
                    <Button onClick={() => handleDelete(id)}>
                        Delete
                    </Button>
                    <Button >
                        <Link to={`/account/${id}`}>
                            Details
                        </Link>
                    </Button>
                </>
        },
    ];

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState({
        pagination: {
            current: 1,
            pageSize: 10,
        },
    });

    const getListAccount = async () => {
        await accountService.listAccount()
            .then((res) =>{
                if (res.status === 200){
                    console.log("data", res.data)
                    setData(res.data)
                    setLoading(false)
                } else {
                    alert('Error')
                    setLoading(false)
                }
            })
            .catch((err) => {
                setLoading(false)
                console.log(err)
            })
    }


    useEffect(() => {
        getListAccount();
    }, []);
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
            <Table
                style={{ width: "75%", marginLeft: "20%", marginTop: "100px" }}
                columns={columns}
                dataSource={data}
                pagination={tableParams.pagination}
                loading={loading}
                onChange={handleTableChange}
            />
        </div>
    )
}

export default List
