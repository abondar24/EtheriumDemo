pragma solidity >=0.4.22 <0.9.0;

contract Airline {

    address  chairman;

    struct details {
        uint deposit;
        uint status;
        uint detailsHash;
    }

    mapping(address => details) public balanceDetails;
    mapping(address => uint) membership;

    modifier onlyChairman{
        require(msg.sender == chairman);
        _;
    }

    modifier onlyMember{
        require(membership[msg.sender] == 1);
        _;
    }

    constructor() public payable {
        chairman = msg.sender;
        membership[msg.sender] = 1;
        balanceDetails[msg.sender].deposit = msg.value;
    }


    function register() public payable {
        address airlineFirst = msg.sender;
        membership[airlineFirst] = 1;
        balanceDetails[msg.sender].deposit = msg.value;
    }

    function unregister(address payable airlineLast) public payable {
        if (chairman != msg.sender) {
            revert();
        }

        membership[airlineLast] = 0;
        airlineLast.transfer(balanceDetails[airlineLast].deposit);
        balanceDetails[airlineLast].deposit = 0;
    }

    function request(address destAirline, uint detailsHash) onlyMember public {
        if (membership[destAirline] != 1) {
            revert();
        }

        balanceDetails[msg.sender].status = 0;
        balanceDetails[msg.sender].detailsHash = detailsHash;

    }

    function response(address srcAirline, uint detailsHash, uint done) onlyMember public {
        if (membership[srcAirline] != 1) {
            revert();
        }

        balanceDetails[msg.sender].status = done;
        balanceDetails[srcAirline].detailsHash = detailsHash;
    }

    function settlePayment(address payable destAirline) onlyMember payable public {
        address srcAirline = msg.sender;
        uint amount = msg.value;
        balanceDetails[destAirline].deposit = balanceDetails[destAirline].deposit + amount;

        balanceDetails[srcAirline].deposit = balanceDetails[srcAirline].deposit - amount;

        destAirline.transfer(amount);
    }


}
