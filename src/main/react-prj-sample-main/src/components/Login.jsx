import { RiKakaoTalkFill } from "react-icons/ri";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { setMessage, login } from "../stores/actions";
import jwt_decode from 'jwt-decode'
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect } from "react";

export default function Login() {
  const isLoggedIn = useSelector(state => state.isLoggedIn);
  const userRole = useSelector(state => state.userRole);
  const REST_API_KEY = "8ef1d7ff4079cc081e948ea0988aafea";
  const REDIRECT_URI = "http://localhost:3000/klogin";

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&prompt=login;`

  const [kperson, setKPerson] = useState('')


  const params = new URLSearchParams(window.location.search);
  const code = params.get("code");


  const navigate = useNavigate()

  const message = useSelector(state => state.message)
  const isLogin = useSelector(state => state.isLoggedIn);
  const dispatch = useDispatch();

  const [input, set_input] = useState({
    id: '',
    pw: ''
  })
  const onClickLogin = (e) => {
    e.preventDefault();

    axios.post('/api/auth/signin', {
      username: input.id,
      password: input.pw
    }).then(response => {
      console.log(response)
      if (response.data != null) {
        dispatch(setMessage(response.data))
        console.log(message)
      }
      const token = response.headers.authorization;
      console.log(token)
      localStorage.setItem("accessToken", token)
      const decoded = jwt_decode(token)
      console.log(decoded);

      dispatch(login(decoded.sub));
      console.log(isLogin);
      if (!response.data.message) {
        navigate('/')
        dispatch(setMessage(""))
      }
    }).catch(error => {
      console.log(error);
    });
    set_input({ ...input, pw: "" })
  }

  useEffect(() => {
    if (code) {
      axios.get('/klogin',
        { params: { code: code } }
      ).then(response => {
        console.log(response)
        setKPerson(response.data)
        window.history.pushState(kperson, null, "http://localhost:3000/");
      })
    }
  })


  return (
    <div className="flex flex-col min-h-screen bg-gray-100 border border-gray-300 shadow-xl rounded-xl">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          <h1 className="mb-8 text-2xl text-center text-primary">Login</h1>
          {message ?
            <div>{message}</div>
            :
            <></>}
          <form onSubmit={onClickLogin}>
            <input
              type="text"
              className="w-full p-3 mb-4 bg-white input input-primary"
              name="email"
              placeholder="Email"
              value={input.id}
              onChange={(e) => set_input({ ...input, id: e.target.value })}
            />

            <input
              type="password"
              className="w-full p-3 mb-4 bg-white input input-primary"
              name="password"
              placeholder="Password"
              value={input.pw}
              onChange={(e) => set_input({ ...input, pw: e.target.value })}
            />

            <button
              type="submit"
              className="w-full font-bold bg-blue-500 border-none btn btn-primary"
            >
              로그인
            </button>
          </form>
          <a href={KAKAO_AUTH_URL}>
            <button
              type="submit"
              className="w-full mt-3 font-bold text-black bg-yellow-300 border-none btn btn-warning"
            >

              <RiKakaoTalkFill className="mr-2 text-2xl" />
              카카오로 로그인
            </button>
          </a>
          <div className="flex justify-end mt-3">
            <Link to="/findid">
              <p className="text-xs">아이디 찾기</p>
            </Link>
            <p className="ml-1 mr-1 text-xs text-gray-300">|</p>
            <Link to="/findpw">
              <p className="text-xs">비밀번호 찾기</p>
            </Link>
          </div>
        </div>

        <div className="flex mt-6 text-grey-dark">
          <p className="font-semibold ">회원이 아니신가요?</p>
          <p
            herf="#"
            className="ml-3 font-bold text-blue-700 underline underline-offset-2 hover:cursor-pointer"
          >
            <Link to="/signup/">회원가입</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
