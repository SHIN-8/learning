import * as TodoModule from "./Todo";

export default describe("Todo Module", () => {
    beforeEach(() => {

    })

    afterEach(() => {

    })

    it("onChange", () => {
        const received = TodoModule.onChange({ index: 0, value: "Todo" })
        const expected = { index: 0, value: "Todo" }

        expect(received.type).toBe(TodoModule.OnChangeActionName)
        expect(received.payload).toEqual(expected)
    })

    it("onIncrement", () => {
        const received = TodoModule.onIncrement()

        expect(received.type).toEqual(TodoModule.OnClickInclimentActionName)
        expect(received.payload).toEqual(undefined)
    })

    it("onDecliment", () => {
        const received = TodoModule.onDecliment()
        const expected = {}
        expect(received.type).toEqual(TodoModule.OnClickDeclimentActionName)
        expect(received.payload).toBe(undefined)
    })
});