import { Routes, Route } from "react-router-dom";
import NoMatch from "./NoMatch";

export default function RouterSetup() {
  return (
    <Routes>
      <Route path="*" element={<NoMatch />} />
    </Routes>
  );
}
