const app = document.createElement("div");
app.className = "app";
app.style.textAlign = "center";

let keyFramesStyle = null;
const addAnimation = (animation) => {
    if (!keyFramesStyle) {
        keyFramesStyle = document.createElement('style');
        keyFramesStyle.type = 'text/css';
        document.head.appendChild(keyFramesStyle);
    }
    keyFramesStyle.sheet.insertRule(animation, keyFramesStyle.length);
}


const keyFrames = `@keyframes profile-pic-spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}`
addAnimation(keyFrames)

const profileImg = document.createElement("img");
profileImg.src = "profileImg.svg";
profileImg.style.height = "10rem";
profileImg.style.animation = "profile-pic-spin infinite 20s linear";
app.appendChild(profileImg);

const hello = document.createElement("h2");
hello.textContent = " Hi, I'm Lydia Yuan.";
app.appendChild(hello);

const intro = document.createElement("h3");
intro.textContent = "A Master of Software Development student at University of Utah.";
app.appendChild(intro);

const ad = document.createElement("h1");
ad.textContent = "Please hire me as your 2023 summer SDE intern!";
app.appendChild(ad);

const myInfoLabel = document.createElement("h4");
myInfoLabel.textContent = "My info:";
app.appendChild(myInfoLabel);

const infoUnorderedList = document.createElement("ul");
infoUnorderedList.style.border = "2px solid";
infoUnorderedList.style.borderRadius = "1rem";
infoUnorderedList.style.padding = "1rem 1rem 1rem 2rem";
infoUnorderedList.style.textAlign = "left";
infoUnorderedList.style.width = "fit-content";
infoUnorderedList.style.margin = "0 auto";

const infoList = [
    { text: "my resume", link: "https://www.overleaf.com/read/ztkmzrkzbmjn" },
    { text: "my GitHub", link: "https://github.com/YuanRuQian" },
    { text: "send me an e-mail", link: "mailto:lydiayuan99@gmail.com" }
]

infoList.forEach(info => {
    const li = document.createElement("li");
    const a = document.createElement("a");
    a.href = info.link;
    a.textContent = info.text;
    a.rel = "noopener noreferrer";
    a.target = "_blank";
    li.appendChild(a);
    infoUnorderedList.appendChild(li);
})

app.appendChild(infoUnorderedList);

const body = document.getElementsByTagName("body")[0];
body.appendChild(app);