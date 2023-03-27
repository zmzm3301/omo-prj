import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function FindIdInput() {
  const [info, setInfo] = useState({
    name: "",
    birth: ""
  })
  const [birthday, setbirthday] = useState({
    year: "",
    month: "",
    day: ""
  })
  const [isFindid, setFindid] = useState()
  const navigate = useNavigate();

  const onSubmitFindId = (e) => {
    e.preventDefault();
    info.birth = birthday.year + "-" + birthday.month + "-" + birthday.day
    console.log(info.birth)
    axios.post('/api/auth/search_id', {
      name: info.name,
      birth: info.birth
    }).then(res => {
      console.log(res)
      setFindid(res.data)
      console.log(isFindid)
      // navigate('/findid/result')
    }).catch(error => console.log(error))
  }


  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      <div className="flex flex-col items-center justify-center flex-1 max-w-sm px-2 mx-auto w-96">
        <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">

          {isFindid ?
            <>
              <h1 className="mb-3 text-2xl">{isFindid.name} 님의 아이디는</h1>
              <div className="flex ">
                <p className="mt-2 mb-4 text-gray-400">{isFindid.username}</p>
                <p className="mb-4 ml-2 text-2xl">입니다</p>
              </div>
              <Link to="/login">
                <input
                  type="submit"
                  className="w-full h-12 mt-5 text-white bg-blue-500 border-none rounded-lg hover:bg-blue-600 active:bg-blue-600"
                  value="로그인 하기"
                />
              </Link>
            </>
            :
            <>
              <h1 className="mb-4 text-2xl">아이디 찾기</h1>
              <p className="mb-4 text-gray-400">
                이름과 생년월일을 입력하여
                <br />
                이메일을 찾을 수 있습니다.
              </p>

              <p className="mb-2 text-sm">이름</p>
              <input
                type="text"
                className="w-full mb-3 bg-white border-b-2 border-gray-300"
                name="name"
                placeholder="이름을 입력해 주세요."
                value={info.name}
                onChange={(e) => setInfo({ ...info, name: e.target.value })}

              />

              <p className="mb-2 text-sm">생년월일</p>
              <div className="flex">
                <input
                  type="text"
                  className="w-full mb-3 mr-2 bg-white border-b-2 border-gray-300"
                  name="name"
                  placeholder="생년(4자)"
                  value={birthday.year}
                  onChange={e => setbirthday({ ...birthday, year: e.target.value })}
                />
                <input
                  type="text"
                  className="w-full mb-3 mr-2 bg-white border-b-2 border-gray-300"
                  name="name"
                  placeholder="월(2자)"
                  value={birthday.month}
                  onChange={e => setbirthday({ ...birthday, month: e.target.value })}
                />
                <input
                  type="text"
                  className="w-full mb-3 bg-white border-b-2 border-gray-300"
                  name="name"
                  placeholder="일(2자)"
                  value={birthday.day}
                  onChange={e => setbirthday({ ...birthday, day: e.target.value })}
                />
              </div>
              <form onSubmit={onSubmitFindId}>
                <input
                  type="submit"
                  className="w-full h-12 text-white bg-gray-400 border-none rounded-lg hover:bg-gray-500 active:bg-gray-500"
                  value="이메일 찾기"
                />
              </form>
            </>
          }
        </div>
      </div>
    </div>


  );
}
