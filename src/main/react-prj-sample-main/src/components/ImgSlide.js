import React, { Component } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Fade from "react-reveal/Fade";

// border-radius: 0.5rem
export default class SimpleSlider extends Component {
  render() {
    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      pauseOnHover: false,
    };
    return (
      <Fade right>
        <div
          style={{ margin: "auto", width: "870px" }}
          id="gallery"
          className="pt-20"
        >
          <div id="imgSlideDiv">
            <Slider {...settings}>
              <div>
                <img
                  id="imgSlideBox"
                  src="./img/slide01.png"
                  style={{ height: "400px", width: "100vw" }}
                  alt=""
                />
              </div>
              <div>
                <img
                  id="imgSlideBox"
                  src="./img/slide02.png"
                  style={{ height: "400px", width: "100vw" }}
                  alt=""
                />
              </div>
              <div>
                <img
                  id="imgSlideBox"
                  src="./img/slide03.png"
                  style={{ height: "400px", width: "100vw" }}
                  alt=""
                />
              </div>
              <div>
                <img
                  id="imgSlideBox"
                  src="./img/slide04.png"
                  style={{ height: "400px", width: "100vw" }}
                  alt=""
                />
              </div>
            </Slider>
          </div>
        </div>
      </Fade>
    );
  }
}
