import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function Login() {
    const { login, register } = useAuth();
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isLogin, setIsLogin] = useState(true)
    const [msg, setMsg] = useState("")


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isLogin) {
                await login(username, password);
                navigate("/");
            } else {
                await register(username, password);
                setMsg("Berhasil Register")
                toggleIsLogin()
            }
        } catch {
            setError("Invalid credentials");

        }
    };

    const toggleIsLogin = () => {
        setIsLogin(!isLogin)
        setUsername("")
        setPassword("")
        setError("")
    }

    return (
        <>
            {msg && <ModalStatus handleCloseModal={() => setMsg("")} message={msg} />}
            <div className="min-h-screen flex items-center justify-center bg-gray-100">
                <form
                    onSubmit={handleSubmit}
                    className="bg-white p-6 rounded-lg shadow w-80"
                >
                    <h2 className="text-2xl font-bold mb-4">{isLogin ? "Login" : "Register"}</h2>

                    {error && <p className="text-red-500 text-sm">{error}</p>}

                    <input
                        value={username}
                        className="w-full border p-2 mb-3 rounded"
                        placeholder="Username"
                        onChange={(e) => setUsername(e.target.value)}
                    />

                    <input
                        value={password}
                        type="password"
                        className="w-full border p-2 mb-4 rounded"
                        placeholder="Password"
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <p className="mb-3">{isLogin ? "Dont have an account?" : "Already have an account?"} <button type="button" className="text-red-500 underline" onClick={() => { toggleIsLogin() }}>{isLogin ? "Register" : "Login"}</button> here</p>

                    <button type="submit" className="w-full bg-red-600 text-white p-2 rounded">
                        {isLogin ? "Login" : "Register"}
                    </button>
                </form>
            </div>
        </>
    );
}
