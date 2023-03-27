import axios from "axios";
import e from "cors";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function FindIdInput() {
  const [result, setResult] = useState();
  const [check, setCheck] = useState(false);
  const [code, setCode] = useState();
  const [errorMessage, setErrorMessage] = useState();

  const navigate = useNavigate();
  const [pass, setPassword] = useState({
    password: "",
    passwordConf: ""
  })
  const [info, setInfo] = useState({
    username: "",
    name: "",
    birth: ""
  });
  const [birthday, setBirthday] = useState({
    year: "",
    month: "",
    day: ""
  })

  const onSubmitHandler = (e) => {
    e.preventDefault();
    info.birth = birthday.year + "-" + birthday.month + "-" + birthday.day
    console.log(info)

    axios.post('/api/auth/search_pw', {
      username: info.username,
      name: info.name,
      birth: info.birth
    })
      .then(res => {
        const data = res.data
        console.log(data)
        if (data.check == true) {
          setCheck(true)
          alert("이메일로 전송된 보안코드를 입력해주세요.")
        } else {
          alert("일치하는 정보가 없습니다.")
        }
      })
      .catch(error => {

      })
  }

  const onSubmitCode = (e) => {
    e.preventDefault();

    axios.post('/auth/login/check', {
      email: info.username,
      code: code
    }).then(res => {
      console.log(res)
      setResult(true);
    }).catch(error => {
      console.log(error)
    })
  }

  const onChangePassword = (e) => {
    e.preventDefault();

    axios.post('/api/auth/change_pw', {
      username: info.username,
      newPassword: pass.password,
      confirmNewPassword: pass.passwordConf
    }).then(res => {
      console.log()
      if (res.data.check == false) {
        setErrorMessage(res.data.errorMessage);
        return;
      }
      navigate('/findpw/result')
    }).catch(error => {
      console.log(error)
    })
  }

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto w-96">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
          {result ?
            <>
              <h1 className="mb-4 text-2xl">비밀번호 변경</h1>
              <p className="mb-4 text-gray-400">
                새로운 비밀번호를 입력해주세요.
              </p>
              <input
                type="password"
                className="w-full mb-3 bg-white border-b-2 border-gray-300"
                placeholder="비밀번호"
                value={pass.password}
                onChange={e => setPassword({ ...pass, password: e.target.value })}
              />
              <p className="mb-4 text-gray-400">
                새로운 비밀번호를 다시 입력해주세요.
              </p>
              <input
                type="password"
                className="w-full mb-3 bg-white border-b-2 border-gray-300"
                placeholder="비밀번호 확인"
                value={pass.passwordConf}
                onChange={e => setPassword({ ...pass, passwordConf: e.target.value })}
              />
              {errorMessage ? <p>{errorMessage}</p> : <></>}
              <form onSubmit={onChangePassword}>
                <input
                  type="submit"
                  className="w-full h-12 text-white bg-gray-400 border-none rounded-lg hover:bg-gray-500 active:bg-gray-500"
                  value="비밀번호 변경"
                />
              </form>
            </>
            :
            <>
              <h1 className="mb-4 text-2xl">비밀번호 찾기</h1>
              {check ?
                <>
                  <p className="mb-4 text-gray-400">
                    전송된 보안코드를 입력해주세요.
                  </p>
                  <p className="mb-2 text-sm">보안코드</p>
                  <input
                    className="w-full mb-3 bg-white border-b-2 border-gray-300"
                    placeholder="보안코드"
                    value={code ? code : ""}
                    onChange={e =>
                      setCode(e.target.value)}
                  />
                  <form onSubmit={onSubmitCode}>
                    <input
                      type="submit"
                      className="w-full h-12 text-white bg-gray-400 border-none rounded-lg hover:bg-gray-500 active:bg-gray-500"
                      value="비밀번호 찾기"
                    />
                  </form>
                </>
                :
                <>
                  <p className="mb-4 text-gray-400">
                    이메일, 이름, 생년월일을 입력하여
                    <br />
                    비밀번호를 찾을 수 있습니다.
                  </p>

                  <p className="mb-2 text-sm">아이디</p>
                  <input
                    type="text"
                    className="w-full mb-3 bg-white border-b-2 border-gray-300"
                    name="name"
                    placeholder="아이디를 입력해 주세요."
                    value={info.username}
                    onChange={e => setInfo({ ...info, username: e.target.value })}
                  />

                  <p className="mb-2 text-sm">이름</p>
                  <input
                    type="text"
                    className="w-full mb-3 bg-white border-b-2 border-gray-300"
                    name="name"
                    placeholder="이름을 입력해 주세요."
                    value={info.name}
                    onChange={e => setInfo({ ...info, name: e.target.value })}
                  />

                  <p className="mb-2 text-sm">생년월일</p>
                  <div className="flex">
                    <input
                      type="text"
                      className="w-full mb-3 mr-2 bg-white border-b-2 border-gray-300"
                      name="name"
                      placeholder="생년(4자)"
                      value={birthday.year}
                      onChange={e => setBirthday({ ...birthday, year: e.target.value })}
                    />
                    <input
                      type="text"
                      className="w-full mb-3 mr-2 bg-white border-b-2 border-gray-300"
                      name="name"
                      placeholder="월(2자)"
                      value={birthday.month}
                      onChange={e => setBirthday({ ...birthday, month: e.target.value })}
                    />
                    <input
                      type="text"
                      className="w-full mb-3 bg-white border-b-2 border-gray-300"
                      name="name"
                      placeholder="일(2자)"
                      value={birthday.day}
                      onChange={e => setBirthday({ ...birthday, day: e.target.value })}
                    />
                  </div>
                  <form onSubmit={onSubmitHandler}>
                    <input
                      type="submit"
                      className="w-full h-12 text-white bg-gray-400 border-none rounded-lg hover:bg-gray-500 active:bg-gray-500"
                      value="비밀번호 찾기"
                    />
                  </form>
                </>
              }
            </>
          }
        </div>
      </div>
    </div>
  );
}
