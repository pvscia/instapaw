import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import axiosApi from "../api/axiosApi";
import { useNavigate } from "react-router-dom";
import ModalStatus from "../components/ModalStatus";
import ModalConfirmation from "../components/ModalConfirmation";
import ModalComplaint from "../components/ModalComplaint";
import emptyImg from "../assets/image.png";


export default function Home() {
    const { logout, user } = useAuth();
    const [data, setData] = useState([]);
    const navigate = useNavigate();
    const isAdmin = user.role === "ADMIN";
    const [msg, setMsg] = useState("")
    const [msgConfirm, setMsgConfirm] = useState("")
    const [modalComplaint, setModalComplaint] = useState(false)
    const [selectedComplaint, setSelectedComplaint] = useState(null)


    useEffect(() => {
        getData()
    }, []);

    const getData = () => {
        axiosApi.get(`/complaints/${isAdmin ? "all" : "my"}`)
            .then((res) => setData(res.data || []));
    }

    const handleSave = async (complaint) => {
        await axiosApi.post(`/complaints/`, { complaint, userId: user.id })
        getData()
        setSelectedComplaint(null)
        setModalComplaint(false)
    }

    const handleEdit = async (complaint) => {
        await axiosApi.put(`/complaints/${selectedComplaint.id}`, { complaint })
        getData()
        setSelectedComplaint(null)
        setModalComplaint(false)
    }

    const handleDelete = async () => {
        setMsgConfirm("")
        await axiosApi.delete(`/complaints/${selectedComplaint.id}`)
        getData()
        setSelectedComplaint(null)
        setMsg("Complaint Deleted")
    }

    if (!user) return <div>Loading...</div>;

    return (
        <>
            {msg && <ModalStatus handleCloseModal={() => setMsg("")} message={msg} />}
            {msgConfirm && <ModalConfirmation handleCloseModal={() => setMsgConfirm("")} message={msgConfirm} handleConfirm={handleDelete} />}
            {modalComplaint && <ModalComplaint handleCloseModal={() => setModalComplaint(false)} complaint={selectedComplaint} handleSubmit={selectedComplaint ? handleEdit : handleSave} />}

            <div className="max-w-[1000px] mx-auto flex flex-col">
                <div className="p-6">
                    <div className="flex justify-between mb-4">
                        <h1 className="text-3xl font-bold">{isAdmin ? "All Complaints" : "My Complaints"}</h1>
                        <button
                            onClick={() => {
                                logout();
                                navigate("/login");
                            }}
                            className="text-white border-2 px-5 py-2 rounded-full bg-red-500 cursor-pointer"
                        >
                            Logout
                        </button>
                    </div>

                    <h1 className="text-xl font-bold">
                        Welcome, {user.username}!
                    </h1>

                    {!isAdmin && <button onClick={() => { setModalComplaint(true) }} className="flex w-full rounded-full justify-center items-center bg-red-500 text-white text-center p-2 my-3 cursor-pointer">
                        + Add a complaint
                    </button>}


                    {data?.length > 0 ? (
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                            {data.map((complaint) => (
                                <div key={complaint.id} className="bg-gray-100 rounded-md p-3">
                                    <div className="flex justify-between items-center">
                                        <h1 className="font-bold">{complaint.username}</h1>

                                        {!isAdmin && (
                                            <div className="flex gap-2">
                                                <button
                                                    onClick={() => {
                                                        setSelectedComplaint(complaint);
                                                        setModalComplaint(true);
                                                    }}
                                                    className="text-blue-500 cursor-pointer"
                                                >
                                                    <i className="fa-solid fa-pencil"></i>
                                                </button>

                                                <button
                                                    onClick={() => {
                                                        setSelectedComplaint(complaint);
                                                        setMsgConfirm("Delete Complaint?");
                                                    }}
                                                    className="text-red-500 cursor-pointer"
                                                >
                                                    <i className="fa-solid fa-trash"></i>
                                                </button>
                                            </div>
                                        )}
                                    </div>

                                    <p>{complaint.complaint}</p>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center mt-10">
                            <p className="text-gray-500 mt-2">No complaints yet</p>
                            <img
                                src={emptyImg}
                                alt="No data"
                                className="h-full"
                            />

                        </div>
                    )}

                </div>
            </div>
        </>

    );
}

