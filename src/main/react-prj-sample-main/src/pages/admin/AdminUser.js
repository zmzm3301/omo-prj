import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
export default function AdminUser() {
  const token = localStorage.getItem("accessToken")
  const [memberList, setMemberList] = useState([])
  const [list, setList] = useState([])
  const [isChecked, setIsChecked] = useState(false)

  const onChangeCheck = (member, checked) => {
    setIsChecked(checked)
    setList((prevList) => {
      const index = prevList.findIndex((m) => m.no === member.no);

      if (checked && index === -1) {
        return [...prevList, { no: member.no, roles: member.roles }];
      } else if (!checked && index !== -1) {
        const newList = [...prevList];
        newList.splice(index, 1);
        return newList;
      } else {
        return prevList;
      }
    });
  };

  const onDeleteRepository = (e) => {
    e.preventDefault();
    axios.post('/api/admin/delete', list, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res.data)
      window.location.reload();
    }).catch(error => {
      console.log(error)
    })
  }

  const onSaveRepository = (e) => {
    e.preventDefault();
    axios.post('/api/admin/save', list, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res.data)
      window.location.reload();
    }).catch(error => {
      console.log(error)
    })
  }

  useEffect((e) => {
    console.log(list)
    console.log(memberList)
  }, [list, memberList])

  useEffect((e) => {
    axios.get('/api/admin/member', {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res)
      const member = res.data
      setMemberList(member)
    }).catch(error => {
      console.log(error)
    })
  }, [])
  return (
    <div className="w-screen h-screen overflow-hidden bg-black ">
      <div>
        <div className="grid content-center text-2xl text-white h-14 bg-cyan-700">
          <p className="ml-10" style={{ width: "80%", margin: "auto" }}>
            ADMIN PAGE
          </p>
        </div>
        <div className="text-sm text-white bg-cyan-800">
          <div className="flex" style={{ width: "80%", margin: "auto" }}>
            <Link to="/adminuser">
              <p className="pt-2 pb-2 hover:text-cyan-400 text-cyan-400 hover:cursor-pointer">
                회원 관리
              </p>
            </Link>
            <Link to="/adminboard">
              <p className="pt-2 pb-2 ml-10 hover:text-cyan-400 hover:cursor-pointer">
                게시물 관리
              </p>
            </Link>
            <Link to="/">
              <p className="pt-2 pb-2 ml-10 hover:text-cyan-400 hover:cursor-pointer">
                홈으로
              </p>
            </Link>
          </div>
        </div>
        <div className="" style={{ width: "100%", margin: "auto" }}>
          <div>
            <div style={{ width: "80%", margin: "auto" }}>
              <div className="bg-black">
                <p className="mt-2 mb-2 text-lg text-white">회원 관리</p>

                <table className="board-table" style={{ width: "100%" }}>
                  <thead className="bg-cyan-900">
                    <tr className="pt-1 pb-1 text-center text-white font-bol">
                      <th className="col-1 "></th>
                      <th scope="col" className="col-1">
                        회원번호
                      </th>
                      <th scope="col" className="col-3">
                        아이디
                      </th>
                      <th scope="col" className="col-3">
                        닉네임
                      </th>
                      <th scope="col" className="col-2">
                        이름
                      </th>
                      <th scope="col" className="col-2">
                        가입일
                      </th>
                      <th scope="col" className="col-1">
                        등급
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-black">
                    {memberList.map((member) => (
                      <tr className="text-white" key={member.no}>
                        <td className="pt-1 pb-1 text-center">
                          <input type={"checkbox"} checked={list.some((m) => m.no === member.no)} onChange={(e) => onChangeCheck(member, e.target.checked)} />
                        </td>
                        <td className="pt-1 pb-1 text-sm text-center">{member.no}</td>
                        <td className="pt-1 pb-1 text-sm text-center">{member.username}</td>
                        <td className="pt-1 pb-1 text-sm text-center">{member.nickname}</td>
                        <td className="pt-1 pb-1 text-sm text-center">{member.name}</td>
                        <td className="pt-1 pb-1 text-sm text-center">
                          {member.createdAt.split(" ")[0]}
                        </td>

                        <td>
                          <select
                            className="text-sm text-right bg-black"
                            value={member.authorities[0]}
                            onChange={(e) => {
                              const newAuthorities = member.authorities.map((auth, index) => index === 0 ? { authority: e.target.value } : auth);
                              const newRole = e.target.value
                              setMemberList((prevMemberList) =>
                                prevMemberList.map((prevMember) => {
                                  if (prevMember.no === member.no) {
                                    setList((prevList) => {
                                      const index = prevList.findIndex((m) => m.no === member.no);

                                      if (isChecked && index === -1) {
                                        return [...prevList, { no: member.no, roles: newRole }];
                                      } else if (!isChecked && index !== -1) {
                                        const newList = [...prevList];
                                        newList.splice(index, 1);
                                        return newList;
                                      } else {
                                        return prevList;
                                      }
                                    })
                                    return { ...prevMember, authorities: newAuthorities, roles: [newRole] };
                                  } else {
                                    return prevMember;
                                  }
                                })
                              );
                            }}
                          >
                            {member.authorities.map((auth) => (auth.authority === 'ADMIN' ?

                              <><option value={'ADMIN'} key={auth}>관리자</option>
                                <option value={'USER'}>회원</option></> :
                              <><option value={'USER'} key={auth}>회원</option>
                                <option value={'ADMIN'}>관리자</option></>
                            ))}
                          </select>
                        </td>
                      </tr>
                    ))}


                  </tbody>
                </table>
                <div
                  className="flex justify-end mt-3"
                  style={{ width: "100%", borderTop: "1px solid gray" }}
                >
                  <button className="w-16 h-8 mt-3 mr-5 text-sm text-white bg-blue-500 rounded-md hover:bg-blue-600"
                    onClick={onSaveRepository}
                  >
                    저장
                  </button>
                  <button className="w-16 h-8 mt-3 text-sm text-white bg-red-500 rounded-md hover:bg-red-600"
                    onClick={onDeleteRepository}
                  >
                    삭제
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div></div>
        </div>
      </div>
    </div>
  );
}
