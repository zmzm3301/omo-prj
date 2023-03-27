import React from "react";
import axios from "axios";
import { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { Link } from "react-router-dom";


function KakaoLogin() {
    const [, , removeCookie] = useCookies('nickName')

    const KAKAO_LOGOUT_URL = `http://localhost:3000/klogout`

    const [kperson, setKPerson] = useState('')
    const [nickName, setNickName] = useState('')
    const [authority, setAuthority] = useState('')
    const [email, setEmail] = useState('')

    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");

    function deleteCookie() {
        removeCookie('nickName');   
        axios.get('/deleteCookie')
    }

    useEffect(() => {
        if(code !== null) {
            axios.get('/klogin',
                    {params: {code: code}}
                ).then(response => { 
                    setKPerson(response.data)
                    window.location.assign("http://localhost:3000")
                })
            }
    }, [code, email, kperson, nickName])

    useEffect(()=> {
        // if(kperson !== "" && kperson !== null) {
            axios.get("/getCookie").then(response => {
                var arr = response.data.split(" ")
                const nickName = arr[0];
                const email = arr[1];
                const authority = arr[2];
    
                setNickName(nickName)
                setEmail(email)
                setAuthority(authority)
            })
        // }
    }, [kperson])
console.log(nickName)
    useEffect(() => {
        if(nickName !== "") {
            axios.post("/addkperson", null, {
                params: {
                    nick_name: nickName,
                    email: email,
                    authority: authority,
                }
            })
        }
    }, [email, nickName])

    const POSTURI = "http://localhost:3000/post/list"//eslint-disable-line no-unused-vars

    if (nickName) {
        return  <>
                    <p className="text-white nav-item">{nickName}</p>
                    <a className="w-16 h-8" href={KAKAO_LOGOUT_URL} id="logout" onClick={deleteCookie}>
                        <button className="w-16 h-8 text-white bg-blue-600 border-none rounded-md hover:bg-blue-800">
                            Logout
                        </button>
                    </a>
                </>
    } else {
        return  <button className="w-16 h-8 text-white bg-blue-600 border-none rounded-md hover:bg-blue-800">
                    <Link to="/login">Login</Link>
                </button>
    }

}

export default KakaoLogin;