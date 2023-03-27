import React, { useEffect, useState } from "react";
import Select from "react-select";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import axios from "axios";
import { useDispatch } from "react-redux";
import { setAuthentication } from "../stores/actions";


export default function ChangeInfo() {
  const s = ["남성", "여성"]
  const [info, setInfo] = useState({
    username: "",
    name: "",
    nickname: "",
    password: "",
    birth: "",
    sex: "",
  });
  const [birthday, setBirthday] = useState({
    year: "",
    month: "",
    day: ""
  })
  const [password, setPassword] = useState("");
  const [passwordConf, setPasswordConf] = useState("");
  const authentication = useSelector(state => state.authentication)
  const dispatch = useDispatch();

  const editProfile = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken")
    if (info.password) {
      if (info.password === passwordConf) {
        axios.post('/api/auth/editprofile', {
          nickname: info.nickname,
          birth: birthday.year + "-" + birthday.month + "-" + birthday.day,
          sex: info.sex,
          password: info.password
        }, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }).then(res => {
          console.log(res)
        }).catch(error => {
          console.log(error)
        })
      } else {
        alert("비밀번호가 일치하지 않습니다.")
      }
    } else {
      axios.post('/api/auth/editprofile', {
        nickname: info.nickname,
        birth: birthday.year + "-" + birthday.month + "-" + birthday.day,
        sex: info.sex
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }).then(res => {
        console.log(res)
      }).catch(error => {
        console.log(error)
      })
    }
  }

  const onSubmitPassword = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken")
    axios.post('/api/auth/checkpw', {
      password: password
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res)
      if (!res.data) {
        dispatch(setAuthentication(false))
        return;
      }
      setInfo(res.data)
      setBirthday({
        year: res.data.birth.split("-")[0],
        month: res.data.birth.split("-")[1],
        day: res.data.birth.split("-")[2]
      });
      dispatch(setAuthentication(true))
    }).catch(error => {
      console.log(error)
    })
    setInfo({
      ...info,
      password: ""
    })
  }
  useEffect(() => {

  }, [])

  return (
    <>
      {authentication ?
        <div className="flex flex-col min-h-screen bg-gray-100 border border-gray-300 shadow-xl rounded-xl">
          <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto ">
            <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
              <h1 className="mb-8 text-2xl font-bold text-center text-primary">
                정보 수정
              </h1>
              이메일
              <textarea
                readOnly={true}
                className="w-full p-3 text-sm font-semibold bg-white"
                name="name"
                value={info.username}
              />

              이름
              <textarea
                readOnly={true}
                className="w-full p-3 mb-4 text-sm font-semibold bg-white"
                name="name"
                value={info.name}
                onChange={(e) => setInfo({ ...info, name: e.target.value })}
              />
              닉네임
              <input
                className="w-full p-3 mb-4 text-sm font-semibold bg-white"
                name="nickname"
                value={info.nickname}
                onChange={(e) => setInfo({ ...info, nickname: e.target.value })}
              />

              <input
                type="password"
                className="w-full p-3 mb-4 text-sm font-semibold bg-white input input-primary"
                name="password"
                placeholder="(선택)비밀번호 변경 시 입력해주세요."
                value={info.password}
                onChange={(e) => setInfo({ ...info, password: e.target.value })}
              />
              <input
                type="password"
                className="w-full p-3 mb-4 text-sm font-semibold bg-white input input-primary"
                name="confirm_password"
                placeholder="비밀번호 확인"
                value={passwordConf}
                onChange={(e) => setPasswordConf(e.target.value)}
              />
              <hr className="mb-4 border-dashed " />
              <div className="flex">
                <input
                  type="text"
                  className="w-full p-3 mb-4 text-sm font-semibold bg-white input input-primary"
                  name="year"
                  placeholder="생년(4자)"
                  value={birthday.year}
                  onChange={e => setBirthday({ ...birthday, year: e.target.value })}
                />
                <input className="w-full mb-4 ml-1 mr-1 text-sm font-semibold bg-white input input-primary"
                  placeholder="월(2자리)"
                  value={birthday.month}
                  onChange={e => setBirthday({ ...birthday, month: e.target.value })}
                />

                <input
                  type="text"
                  className="w-full p-3 mb-4 text-sm font-semibold bg-white input input-primary"
                  name="day"
                  placeholder="일(2자리)"
                  value={birthday.day}
                  onChange={e => setBirthday({ ...birthday, day: e.target.value })}
                />
              </div>
              <select className="w-full mb-4 text-sm font-semibold bg-white input input-primary"
                value={info.sex}
                onChange={e => setInfo({ ...info, sex: e.target.value })}>
                {s.map((e) => (
                  <option key={e} value={e}>{e}</option>
                ))}
              </select>

              <button
                type="submit"
                className="w-full btn btn-primary"
                onClick={editProfile}
              >
                수정하기
              </button>
            </div>
          </div>
        </div>
        :
        <>
          <div className="flex flex-col min-h-screen bg-gray-100">
            <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto w-96">
              <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
                <p>비밀번호를 입력해주세요.</p>
                <input
                  type="password"
                  className="w-full mb-3 bg-white border-b-2 border-gray-300"
                  placeholder="비밀번호"
                  value={password}
                  onChange={e => setPassword(e.target.value)}
                />
                <button onClick={onSubmitPassword}
                  className="w-full h-12 text-white bg-gray-400 border-none rounded-lg hover:bg-gray-500 active:bg-gray-500"
                >확인</button>
              </div>
            </div>
          </div>
        </>}
    </>
  );
}
