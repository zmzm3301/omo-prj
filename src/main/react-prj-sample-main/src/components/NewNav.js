import React, { useState, useEffect } from "react";
import { FaBars, FaTimes } from "react-icons/fa";
import { Link as LinkScroll } from "react-scroll";
import { Link, useNavigate } from "react-router-dom";
import { Dropdown } from "react-bootstrap";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../stores/actions";
import "./Navbar.css";
import KakaoLogin from "./KakaoLogin";

const Navbar = () => {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);

  const closeMenu = () => setClick(false);
  const dispatch = useDispatch();
  const navigate = useNavigate()
  const users = useSelector(state => state.user);
  const isLogin = useSelector(state => state.isLoggedIn);
  const nickName = useSelector(state => state.nickName);
  const userRole = useSelector(state => state.userRole)
  const [kpersons, setKPersons] = useState([])
  const [kakaoNickName, setNickName] = useState("")
  const [authority, setAuthority] = useState("")
  const [isAdmin, toggleAdmin] = useState(false)

  useEffect(() => {
    axios.get("kpersons")
    .then(res => {
      setKPersons(res.data)
    })
  }, [])

  useEffect(() => {
    axios.get("/getCookie").then(response => {
      var arr = response.data.split(" ")
      const kakaoNickName = arr[0];
      const authority = arr[2];
      console.log("nick: ", kakaoNickName)
      console.log("auth: ", authority)
      setAuthority(authority)
      setNickName(kakaoNickName)
    })
  }, [kpersons])

  useEffect(() => {
    if(authority === "admin" || authority==="superAdmin") {
      toggleAdmin(true)
    }
  }, [kakaoNickName, kpersons, authority])

  const handleMyBoard = () => {
    navigate('/board/my');
  }
  const handleChangeInfo = () => {
    navigate('/ChangeInfo');
  }


  const onLogoutHandler = () => {
    localStorage.removeItem("accessToken");
    axios.post('/logout', {}).then(res => console.log(res)).catch(error => console.log(error))
    dispatch(logout(users))
    navigate('/')
  }
  return (
    <div className="overflow-visible header">
      <nav className="navbar">
        <div className="hamburger" onClick={handleClick}>
          {click ? (
            <FaTimes size={30} style={{ color: "#ffffff" }} />
          ) : (
            <FaBars size={30} style={{ color: "#ffffff" }} />
          )}
        </div>
        <ul className={click ? "nav-menu active" : "nav-menu"}>
          <li className="nav-item">
            <LinkScroll
              to="hero"
              spy={true}
              smooth={false}
              offset={0}
              duration={500}
              onClick={closeMenu}
            >
              Home
            </LinkScroll>
          </li>
          <li className="nav-item">
            <LinkScroll
              to="robot"
              spy={true}
              smooth={false}
              offset={0}
              duration={500}
              onClick={closeMenu}
            >
              Robot
            </LinkScroll>
          </li>
          <li className="nav-item">
            <LinkScroll
              to="about"
              spy={true}
              smooth={false}
              offset={0}
              duration={500}
              onClick={closeMenu}
            >
              About
            </LinkScroll>
          </li>
          <li className="nav-item">
            <LinkScroll
              to="gallery"
              spy={true}
              smooth={false}
              offset={0}
              duration={500}
              onClick={closeMenu}
            >
              Gallery
            </LinkScroll>
          </li>
          <li className="nav-item">
            <LinkScroll
              to="contact"
              spy={true}
              smooth={false}
              offset={0}
              duration={500}
              onClick={closeMenu}
            >
              Contact
            </LinkScroll>
          </li>
          <li className="nav-item">
            <a href="#demo" onClick={closeMenu}>
              Team
            </a>
          </li>
          <li className="nav-item">
            <a href="/board" onClick={closeMenu}>
              Board
            </a>
          </li>
        </ul>
        <ul>
          {isLogin || kakaoNickName ?
            <Dropdown>
              <Dropdown.Toggle
                variant="success"
                id="dropdown-basic"
                style={{ height: "2rem", minHeight: "1rem" }}
                className="font-bold text-white bg-green-600 border-0 shadow-md hover:bg-green-800"
              >
                {nickName} {kakaoNickName}
              </Dropdown.Toggle>
              <Dropdown.Menu className="border-0 shadow-sm">

                <Dropdown.Item href="/board/my" className="text-sm">
                  내가 쓴 글
                </Dropdown.Item>

                <Dropdown.Item href="/ChangeInfo" className="text-sm">
                  정보 변경
                </Dropdown.Item>
                {userRole === 'ROLE_ADMIN' ?
                  <Dropdown.Item href="/adminuser" className="text-sm">
                    회원 관리
                  </Dropdown.Item>
                  : <></>}
                <Dropdown.Item>
                  <button onClick={onLogoutHandler}>로그아웃</button>
                </Dropdown.Item>



              </Dropdown.Menu>
            </Dropdown>
            :
            <KakaoLogin />
          }
          

        </ul>
      </nav>
    </div>
  );
};

window.addEventListener("scroll", () => {
  const test = document.querySelector(".header");
  if (window.scrollY > 100) {
    test.style.backgroundColor = "rgba(0, 0, 0, .8)";
  } else {
    test.style.backgroundColor = "#00ff0000";
  }
});

export default Navbar;
