"use strict";

let socket = null
let isWebSocketOpen = false

const XValue = document.getElementById("XValue")
const YValue = document.getElementById("YValue")
const CalculateWithWebSocketButton = document.getElementById("web-socket-button")
const CalculateWithAjaxButton = document.getElementById("ajax-button")
const ResultContainer = document.getElementById("result-container")


const getXValue = () => Number(XValue.value)

const clearXValue = () => XValue.value = null

const focusOnXValue = () => XValue.focus()

const getYValue = () => Number(YValue.value)

const clearYValue = () => YValue.value = null

const focusOnYValue = () => YValue.focus()

const updateResult = (result) => ResultContainer.textContent = `Result: ${result}`

const clearResult = () => ResultContainer.textContent = ""

XValue.onchange = clearResult
YValue.onchange = clearResult

const checkIfInputAreValidNumbers = () => {
    const xVal = getXValue()
    const yVal = getYValue()
    if (Number.isNaN(xVal)) {
        alert("plz make sure X is a valid number!")
        clearXValue()
        focusOnXValue()
        return false
    }
    if (Number.isNaN(yVal)) {
        alert("plz make sure Y is a valid number!")
        clearYValue()
        focusOnYValue()
        return false
    }
    return true
}

const handleOnWebSocketError = (event) => console.error(event)

const handleOnWebSocketMessage = (message) => {
    const result = message.data
    updateResult(result)
}

const handleOnWebSocketClose = (event) => console.log(event)

const handleCalculateButtonClickWithWebSocket = () => {
    const xVal = getXValue()
    const yVal = getYValue()
    socket.send(`${xVal} ${yVal}`)
}

const handleCalculateWithWebSocketButtonClick = (event) => {
    if (!checkIfInputAreValidNumbers()) {
        return
    }
    handleCalculateButtonClickWithWebSocket()
}

CalculateWithWebSocketButton.onclick = handleCalculateWithWebSocketButtonClick

const getURL = () => `http://localhost:8080/calculate?x=${getXValue()}&y=${getYValue()}`

const handleCalculateButtonClickWithAjax = () => {
    const request = new XMLHttpRequest()
    request.addEventListener("load", function () { updateResult(this.responseText) })
    request.open("GET", getURL())
    request.send();
}

const handleCalculateWithAjaxButtonClick = (event) => {
    if (!checkIfInputAreValidNumbers()) {
        return
    }
    handleCalculateButtonClickWithAjax()
}

CalculateWithAjaxButton.onclick = handleCalculateWithAjaxButtonClick

const handleEnterKeyDown = (event) => {
    if (event.key === 'Enter') {
        handleCalculateWithWebSocketButtonClick(event)
    }
    if (event.key === 'Shift') {
        handleCalculateWithAjaxButtonClick(event)
    }
}

window.addEventListener("keydown", handleEnterKeyDown)

const handleOnWebSocketOpen = (event) => {
    isWebSocketOpen = true
    const xVal = getXValue()
    const yVal = getYValue()
}

const setupWebSocket = (event) => {
    socket = new WebSocket('ws://localhost:8080');
    socket.addEventListener('open', handleOnWebSocketOpen)
    socket.addEventListener('error', handleOnWebSocketError)
    socket.addEventListener('message', handleOnWebSocketMessage)
    socket.addEventListener('close', handleOnWebSocketClose)
}

window.onload = setupWebSocket