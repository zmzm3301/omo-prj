const express = require("express");
const app = express();
const cors = require("cors");

app.use(cors());
app.use(express.json()); // for parsing application/json
app.use(express.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

let id = 2;
const todoList = [
  {
    id: 1,
    title: "title 1",
    createAt: "creaateAt 1",
    content: "contest 1",
    username: "username 1",
  },
  {
    id: 2,
    title: "title 2",
    createAt: "creaateAt 2",
    content: "contest 2",
    username: "username 2",
  },
  {
    id: 3,
    title: "title 3",
    createAt: "creaateAt 3",
    content: "contest 3",
    username: "username 3",
  },
];

app.get("/board/list", (req, res) => {
  res.json(todoList);
});

app.get("/board/list/:id", (req, res) => {
  res.json(todoList);
});

app.listen(4000, () => {
  console.log("server start!");
});
