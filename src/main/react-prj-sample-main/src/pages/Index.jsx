import Omocard from "../components/Omocard";
import React, { useState } from "react";
import Hero from "../components/Hero";
// import Info from "../components/Info";
import Whats from "../components/Whats";
// import Line from "../components/Line";
import KakaoMap from "../components/KakaoMap";
import ImgSlide from "../components/ImgSlide";
import NewNav from "../components/NewNav";
import Footer from "../components/Footer";

export default function Index() {
  return (
    <div style={{ overflowX: "hidden" }}>
      <NewNav />
      <Hero />
      <Omocard />
      {/* <Info /> */}
      <Whats />
      {/* <Line /> */}
      <ImgSlide />
      <KakaoMap />
      <Footer />
    </div>
  );
}
