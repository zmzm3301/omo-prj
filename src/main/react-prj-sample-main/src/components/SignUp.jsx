import React, { useState } from "react";
import Select from "react-select";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { data } from "autoprefixer";

export default function SignUp() {
  const [info, setInfo] = useState({
    username: "",
    password: "",
    passwordChk: "",
    name: "",
    nickname: "",
    birth: "",
    sex: "",
  });

  const month = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
  ];
  const [selectyear, setselectyear] = useState("2000");
  const [selected, setselect] = useState("01");
  const [selectday, setselectday] = useState("01");
  const [select_s, setselect_s] = useState("---");
  const [checkEmail, setCheckEmail] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [isComplete, setComplete] = useState(false);

  let CheckEmailMessage = "";
  let isCheck_id = false;
  let date = new Date();
  let lastyear = date.getUTCFullYear();
  let Days = [];
  let years = [];
  let i = 1;
  let lastday = 32;
  let mon = selected;
  let y = selectyear;
  const s = ["---", "남성", "여성"];
  const sexoption = (e) => {
    setselect_s(e.target.value);
    setInfo({ ...info, sex: e.target.value });
  };

  const checkId = (e) => {
    e.preventDefault();
    const isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(info.username);
    if (!isValid) {
      CheckEmailMessage = "이메일 형식을 확인해주세요.";
      setEmailMessage(CheckEmailMessage);
      return;
    }
    console.log(info.username);
    axios
      .post("/api/auth/checkid", {
        username: info.username,
      })
      .then((res) => {
        console.log(res.data.check);
        if (res.data.check == false) {
          alert("존재하는 이메일입니다.");
          setEmailMessage("");
          isCheck_id = false;
        } else {
          alert("사용가능한 이메일입니다.\n인증코드를 입력해주세요.");
          isCheck_id = true;
          setEmailMessage("");
        }
      })
      .catch((error) => console.log(error));
  };

  info.birth = selectyear + "-" + selected + "-" + selectday;
  const year_option = (e) => {
    setselectyear(e.target.value);
    setInfo({ ...info, birth: selectyear });
  };

  const monthoption = (e) => {
    setselect(e.target.value);
    setInfo({ ...info, birth: selectyear + "-" + selected });
  };

  const dayoption = (e) => {
    setselectday(e.target.value);
    setInfo({ ...info, birth: selectyear + "-" + selected + "-" + selectday });
  };
  let leapyear = 0;
  for (let v = 1900; v < lastyear + 1; v++) {
    const year = [`${v}`];
    years = [...years, year];
  }
  y = y.slice(0, 4);
  if (y % 400 === 0 || (y % 100 !== 0 && y % 4 === 0)) {
    leapyear = 1;
  }

  if (mon === "2") {
    lastday = 29 + leapyear;
  }
  if (mon === "4" || mon === "6" || mon === "9" || mon === "11") {
    lastday = 31;
  }

  for (i; i < lastday; i++) {
    if (i < 10) {
      const day = [`0${i}`];
      Days = [...Days, day];
    } else {
      const day = [`${i}`];
      Days = [...Days, day];
    }
  }
  const navigate = useNavigate();

  const HandlerSignUp = (e) => {
    e.preventDefault();

    if (isComplete == false) {
      return alert("이메일 인증을 완료해주세요.");
    } else if (info.password != info.passwordChk) {
      return alert("비밀번호가 일치하지 않습니다.");
    }
    console.log(info);
    axios
      .post("/api/auth/signup", {
        username: info.username,
        password: info.password,
        name: info.name,
        nickname: info.nickname,
        birth: selectyear + "-" + selected + "-" + selectday,
        sex: info.sex,
      })
      .then((res) => {
        console.log(res)
        alert("가입이 완료되었습니다.")
      })
      .catch((error) => console.log(error));
  };

  const CheckNicknameHandler = (e) => {
    e.preventDefault();
    if (info.nickname.length < 2) {
      alert("닉네임 양식에 맞지 않습니다.")
      return;
    }
    axios.post('/api/auth/check_nickname', {
      nickname: info.nickname
    }).then(res => console.log(res.data)).catch(error => console.log(error))
  }

  const CheckEmailHandler = (e) => {
    e.preventDefault();
    axios
      .post("/auth/login/check", {
        email: info.username,
        code: checkEmail,
      })
      .then((res) => {
        console.log(res);
        alert("인증이 완료되었습니다.");
        setComplete(true);
      })
      .catch((error) => console.log(error));
  };

  return (
    <div className="flex flex-col min-h-screen bg-gray-100 border border-gray-300 shadow-xl rounded-xl">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto ">
        <div className="px-6 py-8 text-black bg-white rounded shadow-md w-80">
          <h1 className="mb-8 text-2xl text-center text-primary">Sign Up</h1>
          <div className="">
            <div className="flex justify-between w-full align-middle ">
              <input
                className="w-48 h-5 p-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
                placeholder="이메일"
                style={{ boxSizing: "borderBox" }}
                type="text"
                value={info.username}
                onChange={(e) => {
                  setInfo({ ...info, username: e.target.value });
                  isCheck_id = false;
                }}
              />
              <button
                onClick={checkId}
                className="w-16 h-8 text-sm text-white bg-blue-500 rounded-md"
              >
                중복확인
              </button>
            </div>
            <div className="flex">
              <p className="ml-4 text-sm">{emailMessage}</p>
              <p className="text-white">.</p>
            </div>
          </div>

          <div className="flex justify-between mb-3">
            <input
              className="w-48 h-5 p-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
              placeholder="인증번호를 입력해 주세요."
              type="text"
              value={checkEmail}
              onChange={(e) => {
                setCheckEmail(e.target.value)
                setComplete(false)
              }}
            />
            {isComplete ? (
              <button>인증완료</button>
            ) : (
              <button
                className="w-16 h-8 text-sm text-white bg-blue-500 rounded-md"
                onClick={CheckEmailHandler}
              >
                인증
              </button>
            )}
          </div>

          <input
            className="w-48 h-5 p-3 mb-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            placeholder="비밀번호"
            type="password"
            value={info.password}
            onChange={(e) => setInfo({ ...info, password: e.target.value })}
          />
          <input
            className="w-48 h-5 p-3 mb-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            placeholder="비밀번호 확인"
            type="password"
            value={info.passwordChk}
            onChange={(e) => setInfo({ ...info, passwordChk: e.target.value })}
          />
          <input
            className="w-48 h-5 p-3 mb-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            placeholder="이름"
            type="text"
            value={info.name}
            onChange={(e) => setInfo({ ...info, name: e.target.value })}
          />

          <input
            className="w-48 h-5 p-3 mb-3 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            placeholder="닉네임"
            type="text"
            value={info.nickname}
            onChange={(e) => setInfo({ ...info, nickname: e.target.value })}
          />

          <button onClick={CheckNicknameHandler}>중복확인</button>

          <hr className="mb-3 border-dashed " />
          <p className="mb-1 text-sm">생년월일</p>
          <div className="flex mb-3">
            <select
              className="h-8 mr-1 text-sm font-semibold bg-white border-blue-500 rounded-md w-22 input input-primary"
              onChange={year_option}
              value={selectyear}
            >
              {years.map((year) => (
                <option value={year} key={year}>
                  {year}
                </option>
              ))}
            </select>
            <select
              onChange={monthoption}
              value={selected}
              className="w-full h-8 ml-1 mr-1 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            >
              {month.map((item) => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </select>
            <select
              onChange={dayoption}
              value={selectday}
              className="w-full h-8 ml-1 text-sm font-semibold bg-white border-blue-500 rounded-md input input-primary"
            >
              {Days.map((Day) => (
                <option value={Day} key={Day}>
                  {Day}
                </option>
              ))}
            </select>
          </div>
          <p className="mb-1 text-sm">성별</p>
          <select
            onChange={sexoption}
            value={select_s}
            className="h-8 mb-4 text-sm font-semibold bg-white border-blue-500 rounded-md w-30 input input-primary"
          >
            {s.map((s) => (
              <option value={s} key={s}>
                {s}
              </option>
            ))}
          </select>
          <form onSubmit={HandlerSignUp}>
            <input
              type="submit"
              className="w-full bg-blue-500 border-none btn btn-primary"
              value="가입하기"
            />
          </form>
        </div>

        <div className="flex content-center justify-center mt-3 text-grey-dark">
          <p className="mt-1 text-sm font-semibol ">이미 가입을 하셨나요?</p>
          <p
            herf="#"
            className="ml-3 font-bold text-blue-700 underline underline-offset-2 hover:cursor-pointer"
          >
            <Link to="/login" className="text-sm">
              로그인
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
