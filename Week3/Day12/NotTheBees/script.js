"use strict";

const canvas = document.getElementById("canvas")
canvas.width = window.innerWidth
canvas.height = window.innerHeight

const context = canvas.getContext("2d")



const canvasWidth = canvas.width
const canvasHeight = canvas.height

const imageSize = 50

let cursorX = 0
let cursorY = 0

const getRotationDegree = (bee) => {
    if (bee.isHeadingRight && bee.isHeadingUp) return 180 - 45
    if (bee.isHeadingRight && !bee.isHeadingUp) return 180 + 45
    if (!bee.isHeadingRight && bee.isHeadingUp) return 45
    if (!bee.isHeadingRight && !bee.isHeadingUp) return - 45
}

const drawASingleBee = (bee) => {
    const { xPos, yPos, image } = bee
    context.save()
    context.translate(xPos + imageSize / 2, yPos + imageSize / 2)
    context.rotate(getRotationDegree(bee) * Math.PI / 180)
    context.drawImage(image, -imageSize / 2, -imageSize / 2, imageSize, imageSize)
    context.restore()
}

const generateRandomBee = () => {
    const bee = {
        xPos: canvasWidth * Math.random(),
        yPos: canvasHeight * Math.random(),
        speed: Math.random() * 2,
        image: new Image(imageSize, imageSize),
        isHeadingRight: true,
        isHeadingUp: true,
    }
    bee.image.src = "bee.svg"
    return bee
}

const getRandomBeesLength = () => Math.max(Math.floor(Math.random() * 15), 1) + 5

const bees = Array(getRandomBeesLength()).fill(null).map(generateRandomBee)

const eraseASingleBee = (bee) => {
    context.clearRect(bee.xPos, bee.yPos, imageSize, imageSize);
}

const updateBeePosition = (bee) => {
    if (bee.xPos <= 0) {
        bee.isHeadingRight = true
    }
    if (bee.xPos >= canvasWidth) {
        bee.isHeadingRight = false
    }
    if (bee.yPos <= 0) {
        bee.isHeadingUp = false
    }
    if (bee.yPos >= canvasHeight) {
        bee.isHeadingUp = true
    }
    bee.xPos += (bee.isHeadingRight ? 1 : -1) * bee.speed
    bee.yPos += (bee.isHeadingUp ? -1 : 1) * bee.speed
}

const checkIfGameIsEnded = () => {
    const areBeesMeetingHoneypot = bees.map(isBeeMeetingHoneypot)
    return areBeesMeetingHoneypot.some(el => el)
}

const updateBeeMovingDirections = (bee) => {
    bee.isHeadingRight = bee.xPos < cursorX
    bee.isHeadingUp = bee.yPos > cursorY
}

const drawBees = () => {
    const ifGameIsEnded = checkIfGameIsEnded()
    if (ifGameIsEnded) {
        window.cancelAnimationFrame(handleWindowOnLoad)
        endTheGame()
    } else {
        bees.forEach(eraseASingleBee)
        bees.forEach(updateBeeMovingDirections)
        bees.forEach(updateBeePosition)
        bees.forEach(drawASingleBee)
        window.requestAnimationFrame(drawBees)
    }
}


const calculateBeeHoneypotDistance = (bee) => {
    const deltaX = cursorX - bee.xPos
    const deltaY = cursorY - bee.yPos
    return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))
}

const isBeeMeetingHoneypot = (bee) => {
    const honeypotRadius = imageSize / 2 * Math.sqrt(2)
    const beeHoneypotDistance = calculateBeeHoneypotDistance(bee)
    return beeHoneypotDistance <= honeypotRadius
}

const handleWindowOnLoad = window.requestAnimationFrame(drawBees)

const generateEndingDiv = () => {
    const ending = document.createElement("div")
    ending.style.textAlign = "center"
    ending.style.display = "flex"
    ending.style.flexDirection = "column"
    ending.style.alignItems = "center"
    ending.style.marginTop = `${canvasHeight / 4}px`
    return ending
}

const showEndingTip = () => {
    const ending = generateEndingDiv()
    const oops = document.createElement("h1")
    oops.textContent = "🐝 Oops...... some bees just caught you!"
    const refreshTip = document.createElement("h1")
    refreshTip.textContent = "🍯 Refresh the page to start again!"
    ending.appendChild(oops)
    ending.appendChild(refreshTip)
    document.body.appendChild(ending)
}

const endTheGame = () => {
    canvas.remove()
    showEndingTip()
}

const handleOnWindowMouseMove = (event) => {
    cursorX = event.x
    cursorY = event.y
}

window.onload = handleWindowOnLoad

window.onmousemove = handleOnWindowMouseMove