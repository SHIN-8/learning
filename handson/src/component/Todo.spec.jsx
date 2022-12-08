import * as React from "react"
import * as ReactDom from "react-dom"
import * as Enzyme from "enzyme"
import { Todo } from "./Todo"

const Adapter = require("enzyme-adapter-react-16")
Enzyme.configure({ adapter: new Adapter() })

const EmptyHandler = () => {

}

export default describe("Todo Module", () => {
    beforeEach(() => {

    })

    afterEach(() => {

    })

    it("add", () => {
        const component = Enzyme.shallow(<Todo
            todo={[]}
            onChange={EmptyHandler}
            onDecliment={EmptyHandler}
            onIncrement={EmptyHandler}
        />)
        expect(component).toMatchSnapshot()
    })

    it("add", () => {
        const component = Enzyme.shallow(<Todo
            todo={[]}
            onChange={EmptyHandler}
            onDecliment={EmptyHandler}
            onIncrement={EmptyHandler}
        />)
        expect(component).toMatchSnapshot()
    })

    it("add", () => {
        const component = Enzyme.shallow(<Todo
            todo={[]}
            onChange={EmptyHandler}
            onDecliment={EmptyHandler}
            onIncrement={EmptyHandler}
        />)
        expect(component).toMatchSnapshot()
    })
});