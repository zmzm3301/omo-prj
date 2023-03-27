import { AiFillYoutube } from "react-icons/ai";
import { AiFillFacebook } from "react-icons/ai";
import { AiFillInstagram } from "react-icons/ai";
import { AiOutlineTwitter } from "react-icons/ai";
import Fade from "react-reveal/Fade";

export default function Info() {
  return (
    <div style={{ margin: "auto", width: "870px" }}>
      <Fade bottom>
        <div className="flex justify-between mt-5 bg-white rounded-lg shadow-lg">
          <div>
            <p className="p-4 text-sm">
              오모로봇의 다양한 정보를 확인해보세요.
            </p>
          </div>
          <div className="flex items-center ">
            <div className="flex items-center justify-center mr-4 bg-gray-100 rounded-full cursor-pointer hover:text-red-500 h-14 w-14">
              <AiFillYoutube className="text-4xl" />
            </div>
            <div className="flex items-center justify-center mr-4 bg-gray-100 rounded-full cursor-pointer h-14 w-14 hover:text-sky-700">
              <AiFillFacebook className="text-4xl" />
            </div>
            <div className="flex items-center justify-center mr-4 bg-gray-100 rounded-full cursor-pointer h-14 w-14 hover:text-fuchsia-700">
              {" "}
              <AiFillInstagram className="text-4xl" />
            </div>
            <div className="flex items-center justify-center mr-4 bg-gray-100 rounded-full cursor-pointer h-14 w-14 hover:text-blue-500">
              {" "}
              <AiOutlineTwitter className="text-4xl" />
            </div>
          </div>
        </div>
      </Fade>
    </div>
  );
}
