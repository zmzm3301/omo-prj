import NewNavBoard from "../../components/NewNavBoard";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export default function BoardMy() {
  const [post, setPost] = useState([]);
  useEffect(() => {
    const token = localStorage.getItem("accessToken")
    axios.post('/api/post/myboard', null, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).then(res => {
      console.log(res)
      setPost(res.data)
    }).catch(error => {
      console.log(error)
    })
  }, [])
  return (
    <div>
      <NewNavBoard />
      <div style={{ margin: "auto", width: "60%" }} className="h-screen">
        <div>.</div>
        <p className="mt-20 text-2xl font-bold">내가 쓴 글</p>
        <table class="board-table mt-5 ">
          <thead>
            <tr className="text-center">
              <th scope="col" className="pb-3 col-1">
                번호
              </th>
              <th scope="col" className="pb-3 col-2">
                제목
              </th>
              <th scope="col" className="pb-3 col-1">
                이름
              </th>
              <th scope="col" className="pb-3 col-1">
                등록일
              </th>
            </tr>
          </thead>
          {post ?
            post.map((p) => (
              <tbody className="border-t-2 border-black" key={p.id}>
                <tr className="border-b border-gray-300">
                  <td className="pt-2 pb-2 text-sm text-center">{p.notice ? "[공지]" : p.id}</td>
                  <th>
                    <Link to={`/board/detail/${p.id}`} className="text-sm">
                      {p.notice ? "[공지사항]" + p.title : p.title}
                    </Link>
                  </th>
                  <td className="pt-2 pb-2 text-sm text-center">{p.author_id}</td>
                  <td className="pt-2 pb-2 text-sm text-center">{p.updatedAt ? p.updatedAt : p.createdAt}</td>
                </tr>
              </tbody>
            ))
            :
            <tbody className="border-t-2 border-black ">
              <tr className="border-b border-gray-300">
                <td className="pt-2 pb-2 text-sm text-center">3</td>
                <th>
                  <p>작성한 게시글이 없습니다.</p>
                </th>
              </tr>
            </tbody>
          }
        </table>
        <div className="flex justify-end mt-4"></div>
        <div className="flex justify-center mt-2">
          <p className="w-4 text-sm text-center">1</p>
        </div>
      </div>
    </div>
  );
}
