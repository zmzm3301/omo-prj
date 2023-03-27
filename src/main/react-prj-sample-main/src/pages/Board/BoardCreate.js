import NewNavBoard from "../../components/NewNavBoard";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

export default function BoardCreate() {
  const isLoggedIn = useSelector(state => state.isLoggedIn)
  const userRole = useSelector(state => state.userRole)
  const [post, setPost] = useState({
    check: false,
    title: "",
    content: ""
  });
  const navigate = useNavigate();

  const onChecked = (e) => {
    if (post.check) {
      setPost({ ...post, check: false })
    } else {
      setPost({ ...post, check: true })
    }
    console.log(post)
  }

  const CreatePost = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken");
    console.log(token)
    axios.post('/api/post/add_post', {
      notice: post.check,
      title: post.title,
      content: post.content
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res)
      alert(res.data)
      navigate('/board')
    }).catch(error => {
      console.log(error)
      alert("게시물 등록에 실패했습니다.")
    })
  }

  return (
    <div>
      <NewNavBoard />
      <div style={{ margin: "auto", width: "60%" }} className="h-screen" id="boardCreateDiv">
        <div>.</div>
        <p className="mt-20 text-2xl font-bold">글쓰기</p>
        <div className="mt-5">
          <form onSubmit={CreatePost}>
            <div>
              {isLoggedIn && userRole == 'ROLE_ADMIN' ?
                <p><input type="checkbox" value={post.check} onChange={onChecked} /> 공지사항</p>
                :
                <></>
              }
              <p className="mb-2 font-bold">제목</p>
              <input
                type="text"
                className="p-1 text-sm bg-white"
                style={{ width: "100%", border: "1px solid rgb(0,0,0)" }}
                placeholder="제목을 입력하세요."
                value={post.title}
                onChange={e =>
                  setPost({ ...post, title: e.target.value })
                }
              />
            </div>
            <div>
              <p className="mt-4 mb-2 font-bold">내용</p>
              <textarea
                className="p-1 text-sm bg-white border-2 border-black resize-none"
                style={{
                  width: "100%",
                  height: "50vh",
                  border: "1px solid rgb(0,0,0)",
                }}
                placeholder="내용을 입력하세요."
                value={post.content}
                onChange={e => setPost({
                  ...post, content: e.target.value
                })}
              />
            </div>
            <div className="flex justify-center mt-3">
              <input type="submit"
                className="h-10 text-sm text-white bg-blue-500 rounded-lg w-28 hover:bg-blue-600" value="등록하기" />
            </div>
          </form>

        </div>
      </div>
    </div>
  );
}
