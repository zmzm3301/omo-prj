import { AiFillCar } from "react-icons/ai";
import { GiWeightScale } from "react-icons/gi";
import { GiConsoleController } from "react-icons/gi";
import { RiBattery2ChargeLine } from "react-icons/ri";
import React, { useState } from "react";
import Fade from "react-reveal/Fade";

export default function Whats() {
  const [mouseOver, setMouseOver] = useState(false);
  const [mouseOverTwo, setMouseOverTwo] = useState(false);
  const [mouseOverThree, setMouseOverThree] = useState(false);
  const [mouseOverFour, setMouseOverFour] = useState(false);

  return (
    <div id="about" className="pt-20">
      <div id="whatsDiv" style={{ margin: "auto", width: 870 }}>
        <Fade bottom>
          <p id="sectionP" className="text-2xl font-bold ">
            (로봇이름)은?
          </p>
        </Fade>
        <Fade bottom>
          <div
            className="flex justify-between mx-auto mt-4 mb-5"
            id="whatsGroup"
          >
            <div id="whatsGroup2" className="flex">
              <div
                id="omo-card"
                className={
                  mouseOver
                    ? "text-center rounded-lg shadow-lg bg-red-500 w-52 h-60 duration-300 mr-3"
                    : "text-center rounded-lg shadow-lg w-52 h-60 duration-300 mr-3"
                }
                onMouseOver={() => {
                  setMouseOver((mouseOver) => !mouseOver);
                }}
                onMouseLeave={() => {
                  setMouseOver((mouseOver) => !mouseOver);
                }}
              >
                <div className="flex justify-center mb-3">
                  <AiFillCar
                    className={
                      mouseOver
                        ? "mt-8 text-5xl text-white duration-300"
                        : "mt-8 text-5xl text-red-500 duration-300"
                    }
                  />
                </div>
                <p
                  className={
                    mouseOver
                      ? "mb-3 text-md font-semibold text-white duration-300"
                      : "mb-3 text-md font-semibold duration-300"
                  }
                >
                  완벽한 자율주행
                </p>
                <p
                  className={
                    mouseOver
                      ? "ml-4 mr-4 text-white duration-300 text-sm"
                      : "ml-4 mr-4 duration-300 text-sm"
                  }
                >
                  아무런 조작 없이 장애물을 피해서 정해진 경로로 반복 이동할 수
                  있습니다.
                </p>
              </div>

              <div
                id="omo-card"
                className={
                  mouseOverTwo
                    ? "text-center rounded-lg shadow-lg bg-red-500 w-52 h-60 duration-300"
                    : "text-center rounded-lg shadow-lg w-52 h-60 duration-300"
                }
                onMouseOver={() => {
                  setMouseOverTwo((mouseOverTwo) => !mouseOverTwo);
                }}
                onMouseLeave={() => {
                  setMouseOverTwo((mouseOverTwo) => !mouseOverTwo);
                }}
              >
                <div className="flex justify-center mb-3">
                  <GiWeightScale
                    className={
                      mouseOverTwo
                        ? "mt-8 text-5xl text-white duration-300"
                        : "mt-8 text-5xl text-red-500 duration-300"
                    }
                  />
                </div>
                <p
                  className={
                    mouseOverTwo
                      ? "mb-3 text-md font-semibold text-white duration-300"
                      : "mb-3 text-md font-semibold duration-300"
                  }
                >
                  넉넉한 적재 중량
                </p>
                <p
                  className={
                    mouseOverTwo
                      ? "ml-4 mr-4 text-white duration-300 text-sm"
                      : "ml-4 mr-4 duration-300 text-sm"
                  }
                >
                  최대 00kg까지 적재가 가능합니다.
                </p>
              </div>
            </div>
            <div className="flex" id="whatsGroup3">
              <div
                id="omo-card"
                className={
                  mouseOverThree
                    ? "text-center rounded-lg shadow-lg bg-red-500 w-52 h-60 duration-300"
                    : "text-center rounded-lg shadow-lg w-52 h-60 duration-300"
                }
                onMouseOver={() => {
                  setMouseOverThree((mouseOverThree) => !mouseOverThree);
                }}
                onMouseLeave={() => {
                  setMouseOverThree((mouseOverThree) => !mouseOverThree);
                }}
              >
                <div className="flex justify-center mb-3">
                  <GiConsoleController
                    className={
                      mouseOverThree
                        ? "mt-8 text-5xl text-white duration-300"
                        : "mt-8 text-5xl text-red-500 duration-300"
                    }
                  />
                </div>
                <p
                  className={
                    mouseOverThree
                      ? "mb-3 text-md font-semibold text-white duration-300"
                      : "mb-3 text-md font-semibold duration-300"
                  }
                >
                  수동 조작 기능
                </p>
                <p
                  className={
                    mouseOverThree
                      ? "ml-4 mr-4 text-white duration-300 text-sm"
                      : "ml-4 mr-4 duration-300 text-sm"
                  }
                >
                  수동 조작을 통하여 원하는 곳으로 물건을 운반할 수 있습니다.
                </p>
              </div>
              <div
                id="omo-card"
                className={
                  mouseOverFour
                    ? "ml-3 text-center rounded-lg shadow-lg bg-red-500 w-52 h-60 duration-300"
                    : "ml-3 text-center rounded-lg shadow-lg w-52 h-60 duration-300"
                }
                onMouseOver={() => {
                  setMouseOverFour((mouseOverFour) => !mouseOverFour);
                }}
                onMouseLeave={() => {
                  setMouseOverFour((mouseOverFour) => !mouseOverFour);
                }}
              >
                <div className="flex justify-center mb-3">
                  <RiBattery2ChargeLine
                    className={
                      mouseOverFour
                        ? "mt-8 text-5xl text-white duration-300"
                        : "mt-8 text-5xl text-red-500 duration-300"
                    }
                  />
                </div>
                <p
                  className={
                    mouseOverFour
                      ? "mb-3 text-md font-semibold text-white duration-300 "
                      : "mb-3 text-md font-semibold duration-300"
                  }
                >
                  자동 충전
                </p>
                <p
                  className={
                    mouseOverFour
                      ? "ml-4 mr-4 text-white duration-300 text-sm"
                      : "ml-4 mr-4 duration-300 text-sm"
                  }
                >
                  배터리가 없으면 스스로 충전 구역으로 이동하여 충전합니다.
                </p>
              </div>
            </div>
          </div>
        </Fade>
      </div>
      <div
        className="flex items-center justify-center h-56 bg-fixed"
        style={{
          backgroundImage: "url(/img/IMG_8198.jpg)",
          backgroundSize: "cover",
          backgroundColor: "rgb(50, 50, 50)",
          backgroundBlendMode: "multiply",
        }}
      ></div>
    </div>
  );
}
