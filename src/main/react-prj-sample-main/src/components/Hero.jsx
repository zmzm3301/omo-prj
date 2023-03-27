import heroImg from "../img/IMG_8197.jpg";
import Fade from "react-reveal/Fade";

export default function Hero() {
  return (
    <div>
      <div
        className="flex items-center justify-center h-screen bg-fixed"
        style={{
          backgroundImage: "url(/img/IMG_8197.jpg)",
          backgroundSize: "cover",
          backgroundColor: "gray",
          backgroundBlendMode: "multiply",
        }}
        id="hero"
      >
        {/* <img src={heroImg} alt="" /> */}
      </div>
      {/* <Fade>
        <div className="absolute top-60 left-16">
          <p className="fixed text-6xl text-white">OMOROBOT</p>
        </div>
      </Fade>

      <div className="absolute top-80 left-16">
        <Fade left>
          <p className="fixed text-4xl text-white">
            To understand the heart and mind of a person,
          </p>
        </Fade>
      </div>

      <div className="absolute top-96 left-16">
        <Fade left>
          <p className="fixed text-4xl text-white">
            look not at what he has already achieved, but at what he aspires to
            do.
          </p>
        </Fade>
      </div> */}
    </div>
  );
}
