import NewNavBoard from "../../components/NewNavBoard";
import { useNavigate, useParams } from "react-router-dom";
import { AiOutlineDelete } from "react-icons/ai";
import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";



export default function BoardDetail() {
  const navigate = useNavigate();
  const userRole = useSelector(state => state.userRole)
  const { no } = useParams();
  const [detail, setDetail] = useState([]);
  const [comment, setComment] = useState("");
  const [commentList, setCommentList] = useState([]);
  const user = useSelector(state => state.user);
  const isLoggedIn = useSelector(state => state.isLoggedIn);
  const [edit, setEdit] = useState(false);

  const onChangeEdit = (username) => {
    if (!edit && user === username) {
      setEdit(true)
    } else if (user !== username) {
      alert("작성자만 수정할 수 있습니다.")
    } else {
      setEdit(false)
    }
  }

  const onChangeNotice = () => {
    if (detail.notice) {
      setDetail({ ...detail, notice: false })
    } else {
      setDetail({ ...detail, notice: true })
    }
  }

  const onChangePost = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken")

    axios.post(`/api/post/update/${no}`, {
      notice: detail.notice,
      title: detail.title,
      content: detail.content
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => { window.location.reload() }).error(error => console.log(error))
  }

  const onDeletePost = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken")
    axios.delete(`/api/post/delete/${no}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      if (res.data.status === true) {
        alert("성공적으로 삭제완료했습니다.")
        navigate('/board')
      } else {
        alert("삭제에 실패했습니다.")
        return;
      }
    })
      .catch(error => console.log(error))
  }

  const onDeleteComment = (id) => {
    const token = localStorage.getItem("accessToken")


    axios.delete(`/api/comment/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => window.location.reload())
      .catch(error => console.log(error))
  }


  const onSubmitComment = (e) => {
    e.preventDefault();
    if (!isLoggedIn) {
      alert("로그인한 회원만 댓글을 남길 수 있습니다.")
      return;
    }
    const token = localStorage.getItem("accessToken")
    if (comment.length === 0) {
      alert("공백은 댓글로 등록할 수 없습니다.")
      return;
    }
    axios.post(`/api/comment/add/${no}`, {
      comment: comment
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => window.location.reload()).catch(error => console.log(error))
  }
  useEffect(() => {

    axios.get(`/api/post/detail/${no}`).then(res => {
      const details = res.data;
      console.log(details)
      setDetail(details);
    }).catch(error => console.log(error))


    axios.get(`/api/comment/list/${no}`).then(res => {
      const comments = res.data;
      console.log(comments)
      setCommentList(comments);
    }).catch(error => console.log(error))

  }, [no])

  return (
    <div
      style={{ margin: "auto", width: "60%", height: "100%" }}
      id="boardDetailDiv"
    >
      <NewNavBoard />
      {detail ?
        <div className="">
          <div>.</div>
          <div className="mt-20">
            {edit && user === detail.username ?
              <><input type="checkbox" value={detail.notice} onChange={onChangeNotice} />
                <div>
                  <input className="p-1 text-sm bg-white"
                    style={{ width: "100%", border: "1px solid rgb(0,0,0)" }}
                    value={detail.title} onChange={e => setDetail({ ...detail, title: e.target.value })} />
                  <div className="flex items-end mt-2">
                    <p className="">{detail.author_id}</p>
                    <p className="ml-3 text-sm text-gray-500">{detail.updatedAt ? detail.updatedAt : detail.createdAt}</p>
                  </div>
                  <hr className="mt-2" />
                </div>
                <div>

                  <textarea
                    className="mt-3 text-sm bg-white resize-none"
                    style={{
                      width: "100%",
                      height: "50vh",
                    }}
                    value={detail.content}
                    onChange={e => setDetail({ ...detail, content: e.target.value })}
                  >

                  </textarea>
                </div>
                <div className="flex justify-end mt-3 ">
                  <button
                    onClick={onChangePost}
                    className="w-16 h-8 mr-5 text-sm font-bold text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                    수정완료
                  </button>
                </div>
              </>
              :
              <>
                <div>
                  <p className="font-bold">
                    {detail.notice ? "[공지사항]" + detail.title : detail.title}
                  </p>
                  <div className="flex items-end mt-2">
                    <p className="">{detail.author_id}</p>
                    <p className="ml-3 text-sm text-gray-500">{detail.updatedAt ? detail.updatedAt : detail.createdAt}</p>
                  </div>
                  <hr className="mt-2" />
                </div>
                <div>
                  <textarea
                    readOnly
                    className="mt-3 text-sm bg-white resize-none"
                    style={{
                      width: "100%",
                      height: "50vh",
                    }}
                    value={detail.content}
                  >

                  </textarea>
                </div>

              </>
            }
            {((isLoggedIn && user === detail.username) || userRole === "ROLE_ADMIN") && !edit ?
              <div className="flex justify-end mt-3 ">
                <button onClick={() => onChangeEdit(detail.username)}
                  className="w-16 h-8 mr-5 text-sm font-bold text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  수정
                </button>
                <button onClick={onDeletePost}
                  className="w-16 h-8 text-sm font-bold text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  삭제
                </button>
              </div>
              :
              <></>
            }
          </div>

          <hr className="mt-3 border-2 border-gray-400 opacity-100" />

          {commentList.map((e) => (

            <div id={e.id} key={e.id}>
              <div className="flex mt-3">
                <p className="text-sm col-2">{e.author_id}</p>
                <p className="text-sm col-8">
                  {e.comment}
                </p>

                <p className="text-sm col-1">{e.created_at.split(".", [1])}</p>
                {isLoggedIn && user === e.author_email ?
                  <form onSubmit={() => onDeleteComment(e.id)}>
                    <button className="col-1" type="submit">
                      <AiOutlineDelete className="" />
                    </button>
                  </form>
                  :
                  <></>
                }
              </div>
            </div>
          ))}
          <div className="pb-20 mt-4">
            <textarea
              className="p-3 text-sm bg-white border-2 border-gray-300 rounded-lg resize-none"
              style={{ width: "100%", height: "10vw" }}
              placeholder="댓글을 입력하세요."
              value={comment}
              onChange={(e) => setComment(e.target.value)}
            ></textarea>
            <div className="flex justify-end mt-2 font-bold ">
              <form onSubmit={onSubmitComment}>
                <button type="submit"
                  className="w-16 h-8 text-sm text-white bg-green-600 rounded-lg hover:bg-green-700">
                  등록
                </button>
              </form>
            </div>
          </div>
        </div>
        :
        <></>
      }
    </div>

  );
}
