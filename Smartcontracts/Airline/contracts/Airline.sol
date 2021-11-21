pragma solidity >=0.4.22 <0.9.0;

contract Airline {

    address  chairperson;

    struct Request {
        uint id;
        uint flightId;
        uint passengerId;
        uint seatsNum;
        address dstAirline;
    }

    struct Response {
        uint id;
        bool status;
        address srcAirline;
    }

    mapping(address => uint) public escrows;
    mapping(address => uint) memberships;
    mapping(address => uint) settledRequestIds;
    mapping(address => Request) requests;
    mapping(address => Response) responses;


    modifier onlyChairperson{
        require(msg.sender == chairperson);
        _;
    }

    modifier onlyMember{
        require(memberships[msg.sender] == 1);
        _;
    }

    constructor() public payable {
        chairperson = msg.sender;
        memberships[msg.sender] = 1;
        escrows[msg.sender] = msg.value;
    }


    function register() public payable {
        address airline = msg.sender;
        memberships[airline] = 1;
        escrows[airline] = msg.value;
    }

    function unregister(address payable airline) public onlyChairperson {

        memberships[airline] = 0;
        airline.transfer(escrows[airline]);
        escrows[airline] = 0;
    }

    function request(uint requestId, uint flightId, uint passengerId, uint seatsNum, address dstAirline) onlyMember public {
        require(memberships[dstAirline] == 1);

        requests[msg.sender] = Request(requestId, flightId, passengerId, seatsNum, dstAirline);

    }

    function response(address srcAirline, uint requestId, bool done) onlyMember public {
        if (memberships[srcAirline] != 1) {
            revert();
        }

        responses[msg.sender].status = done;
        responses[msg.sender].srcAirline = srcAirline;
        responses[msg.sender].id = requestId;
    }

    function settlePayment(uint requestId, uint seatsNum, address payable dstAirline) onlyMember payable public {
        address srcAirline = msg.sender;
        uint amount = 500000000;
        escrows[dstAirline] = escrows[dstAirline] + amount * seatsNum;

        escrows[srcAirline] = escrows[srcAirline] - amount * seatsNum;

        settledRequestIds[srcAirline] = requestId;
    }

    function replenishEscrow() payable public {
        escrows[msg.sender] = escrows[msg.sender] + msg.value;
    }


}
