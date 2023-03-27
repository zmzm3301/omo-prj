import { Link } from "react-router-dom";
export default function AdminBoard() {
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
              <p className="pt-2 pb-2 hover:text-cyan-400 hover:cursor-pointer">
                회원 관리111111
              </p>
            </Link>
            <Link to="/adminboard">
              <p className="pt-2 pb-2 ml-10 hover:text-cyan-400 hover:cursor-pointer text-cyan-400">
                게시물 관리22222
              </p>
            </Link>
            <Link to="/">
              <p className="pt-2 pb-2 ml-10 hover:text-cyan-400 hover:cursor-pointer">
                홈으로333333
              </p>
            </Link>
          </div>
        </div>
        <div className="" style={{ width: "100%", margin: "auto" }}>
          <div>
            <div style={{ width: "80%", margin: "auto" }}>
              <div className="bg-black">
                <p className="mt-2 mb-2 text-lg text-white">게시물 관리</p>
                <table className="board-table" style={{ width: "100%" }}>
                  <thead className="bg-cyan-900">
                    <tr className="text-center text-white font-bol">
                      <th className="col-1"></th>
                      <th scope="col" className="col-1">
                        번호
                      </th>
                      <th scope="col" className="col-5">
                        제목
                      </th>
                      <th scope="col" className="col-3">
                        닉네임
                      </th>
                      <th scope="col" className="col-3">
                        등록일
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-black">
                    <tr className="text-white">
                      <td className="pt-1 pb-1 text-center">
                        <input type={"checkbox"} />
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        testNumber3
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">제목3</td>
                      <td className="pt-1 pb-1 text-sm text-center">닉네임3</td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        2023.01.03
                      </td>
                    </tr>

                    <tr className="text-white bg-cyan-900">
                      <td className="pt-1 pb-1 text-center">
                        <input type={"checkbox"} />
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        testNumber2
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">제목2</td>
                      <td className="pt-1 pb-1 text-sm text-center">닉네임2</td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        2023.01.02
                      </td>
                    </tr>
                    <tr className="text-white">
                      <td className="pt-1 pb-1 text-center">
                        <input type={"checkbox"} />
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        testNumber1
                      </td>
                      <td className="pt-1 pb-1 text-sm text-center">제목1</td>
                      <td className="pt-1 pb-1 text-sm text-center">닉네임1</td>
                      <td className="pt-1 pb-1 text-sm text-center">
                        2023.01.01
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div
                  className="flex justify-end mt-3"
                  style={{ width: "100%", borderTop: "1px solid gray" }}
                >
                  <button className="w-16 h-8 mt-3 mr-5 text-sm text-white bg-red-500 rounded-md hover:bg-red-600">
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
