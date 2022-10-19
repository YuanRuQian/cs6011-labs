
const TableSize = 10

let prevClickedTableData = null

const TimesTable = document.getElementById("times-table")

const witeTableHeader = () => {
    const header = document.createElement("tr");
    const firstBlankCell = document.createElement("th")
    header.appendChild(firstBlankCell)
    Array(TableSize).fill(null).forEach((_, index) => {
        const tableHead = document.createElement("th");
        tableHead.textContent = index + 1
        header.appendChild(tableHead)
    })
    TimesTable.appendChild(header)
}

witeTableHeader()

const addClass = (text) => {
    return (event) => {
        const element = event.target
        element.classList.add(text)
    }
}

const removeClass = (text) => {
    return (event) => {
        const element = event.target
        element.classList.remove(text)
    }
}

const addHighlightedClass = addClass("highlighted")
const removeHighlightedClass = removeClass("highlighted")


const handleClick = (event) => {
    if (prevClickedTableData) {
        const element = prevClickedTableData
        element.classList.remove("clicked")
    }
    const element = event.target
    element.classList.add("clicked")
    prevClickedTableData = event.target
}

const getInteractiveTableDataElement = (text) => {
    const tableData = document.createElement("td");
    tableData.textContent = text
    tableData.onmouseenter = addHighlightedClass
    tableData.onmouseleave = removeHighlightedClass
    tableData.onclick = handleClick
    return tableData
}

const writeTableDataRow = (row) => {
    const tableRow = document.createElement("tr");
    const firstRowIndexCell = document.createElement("th")
    firstRowIndexCell.textContent = row
    tableRow.appendChild(firstRowIndexCell)
    Array(TableSize).fill(null).forEach((_, col) => {
        const tableData = getInteractiveTableDataElement(row * (col + 1))
        tableRow.appendChild(tableData)
    })
    TimesTable.appendChild(tableRow)
}

const writeTableData = () => {
    Array(TableSize).fill(null).forEach((_, row) => writeTableDataRow(row + 1))
}

writeTableData()




const blue = 0x0000FF
const red = 0xFF0000


const hexToHexColorString = (hex) => {
    if (hex < blue) {
        hex = blue
    }
    if (hex > red) {
        hex = red
    }
    let string = hex.toString(16)
    if (string.length < 6) {
        string = Array(6 - string.length).fill('0').join('') + string
    }
    return `#${string}`
}

const changeColor = () => {
    // check if an interval has already been set up
    let timer = null
    let colorIndex = blue
    let isColorIncreasing = true
    const step = 500000
    return () => {
        console.log("timer: ", timer)
        if (timer) {
            clearInterval(timer);
            timer = null;
            colorIndex = blue;
            isColorIncreasing = true
        }
        timer = setInterval(() => {
            const body = document.getElementsByTagName("body")[0];
            body.style.backgroundColor = hexToHexColorString(colorIndex);
            if (colorIndex >= red) {
                console.log("red", hexToHexColorString(colorIndex))
                isColorIncreasing = false
            }
            else if (colorIndex <= blue) {
                console.log("blue", hexToHexColorString(colorIndex))
                isColorIncreasing = true
            }
            colorIndex = (isColorIncreasing ? step : -step) + colorIndex
        }, 100);
    }
}

const backgroundBtn = document.getElementById("background-btn")
backgroundBtn.onclick = changeColor()

